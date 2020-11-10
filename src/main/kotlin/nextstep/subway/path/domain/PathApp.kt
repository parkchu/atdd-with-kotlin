package nextstep.subway.path.domain

class PathApp : PathInterface {
    private val _paths2: MutableMap<String, MutableMap<String, Path>> = mutableMapOf()
    private val _visitStation2: MutableList<String> = mutableListOf()

    fun init() {
        _paths2.clear()
        _visitStation2.clear()
    }

    fun setPoint(name: String) {
        checkContainsPoint(name)
        val smallMap = mutableMapOf<String, Path>()
        smallMap[name] = Path(listOf(name), 0, 0)
        _paths2[name] = smallMap
        _paths2.forEach { path1 ->
            _paths2.forEach { path2 ->
                if (!path1.value.contains(path2.key)) {
                    path1.value[path2.key] = Path(listOf(path1.key, path2.key), INF, INF)
                }
            }
        }
    }

    private fun checkContainsPoint(name: String) {
        if (_paths2.contains(name)) {
            throw IllegalArgumentException("$name 은 존재하는 포인트입니다.")
        }
    }

    fun setBetweenValue(point1: String, point2: String, distance: Int, duration: Int) {
        checkSamePoint(point1, point2)
        val path1 = getPathsIt(point1)[point2]
        val path2 = getPathsIt(point2)[point1]
        path1!!.updateDistance(distance)
        path1.updateDuration(duration)
        path2!!.updateDistance(distance)
        path2.updateDuration(duration)
    }

    private fun checkSamePoint(point1: String, point2: String) {
        if (point1 == point2) {
            throw java.lang.IllegalArgumentException("$point1 과 $point2 은 같은 포인트입니다.")
        }
    }

    private fun getPathsIt(point: String): MutableMap<String, Path> {
        return _paths2[point] ?: throw IllegalArgumentException("$point 은 존재하지 않는 포인트입니다.")
    }

    override fun getShortestPath(startPoint: String, arrivalPoint: String): Map<String, List<String>> {
        val paths = dijkstra2(startPoint)
        return paths[arrivalPoint]?.changeMap() ?: throw IllegalArgumentException("$arrivalPoint 은 존재하지 않는 포인트 입니다.")
    }

    private fun dijkstra2(startPoint: String): Map<String, Path> {
        _visitStation2.clear()
        _visitStation2.add(startPoint)
        val paths = getPathsItToMap(startPoint).toMutableMap()
        repeat(paths.size - 1) {
            val pointName = getMinStationId2(startPoint, paths)
            _visitStation2.add(pointName)
            val paths2 = getPathsItToMap(pointName)
            paths2.forEach {
                val path = paths.getValue(pointName)
                if (path.distance + it.value.distance < paths.getValue(it.key).distance) {
                    paths[it.key] = path.add(it.value)
                }
            }
        }
        return paths
    }

    private fun getPathsItToMap(point: String): Map<String, Path> {
        return _paths2[point] ?: throw IllegalArgumentException("$point 은 존재하지 않는 포인트입니다.")
    }

    private fun getMinStationId2(startPoint: String, paths: Map<String, Path>): String {
        var betweenValue: Int = INF
        var pointName: String = startPoint
        paths.forEach {
            if (it.value.distance < betweenValue && !_visitStation2.contains(it.key)) {
                betweenValue = it.value.distance
                pointName = it.key
            }
        }
        return pointName
    }

    fun getPaths(): Map<String, Map<String, Path>> = _paths2.toMap()

    companion object {
        const val INF: Int = 1000000000
    }
}
