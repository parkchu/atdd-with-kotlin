package nextstep.subway.path.domain

class Paths {
    private val _paths: MutableMap<String, MutableMap<String, Path>> = mutableMapOf()
    private val _visitPoint: MutableList<String> = mutableListOf()

    fun init() {
        _paths.clear()
        _visitPoint.clear()
    }

    fun setPoint(name: String) {
        checkContainsPoint(name)
        val smallMap = mutableMapOf<String, Path>()
        smallMap[name] = Path(listOf(name), 0, 0)
        _paths[name] = smallMap
        _paths.forEach { path1 ->
            _paths.forEach { path2 ->
                if (!path1.value.contains(path2.key)) {
                    path1.value[path2.key] = Path(listOf(path1.key, path2.key), INF, INF)
                }
            }
        }
    }

    private fun checkContainsPoint(name: String) {
        if (_paths.contains(name)) {
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
        return _paths[point] ?: throw IllegalArgumentException("$point 은 존재하지 않는 포인트입니다.")
    }

    fun getPaths(): Map<String, Map<String, Path>> = _paths.toMap()

    fun addVisitPoint(point: String) {
        _visitPoint.add(point)
    }

    fun getPathsItToMap(point: String): Map<String, Path> {
        return _paths[point] ?: throw IllegalArgumentException("$point 은 존재하지 않는 포인트입니다.")
    }

    fun getMinStationId2(startPoint: String, paths: Map<String, Path>): String {
        var betweenValue: Int = INF
        var pointName: String = startPoint
        paths.forEach {
            if (it.value.distance < betweenValue && !_visitPoint.contains(it.key)) {
                betweenValue = it.value.distance
                pointName = it.key
            }
        }
        return pointName
    }

    companion object {
        const val INF: Int = 1000000000
    }
}
