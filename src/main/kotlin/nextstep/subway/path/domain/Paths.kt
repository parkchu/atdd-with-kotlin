package nextstep.subway.path.domain

class Paths {
    private val _paths: MutableMap<String, MutableMap<String, Path>> = mutableMapOf()
    private val _visitPoint: MutableList<String> = mutableListOf()
    private val _points = mutableMapOf("출발지점" to "", "도착지점" to "")

    fun clearPaths() {
        _paths.clear()
    }

    fun clearVisitPoint() {
        _visitPoint.clear()
    }

    fun setPoint(name: String) {
        checkContainsPoint(name)
        val smallMap = mutableMapOf<String, Path>()
        smallMap[name] = Path(listOf(name), 0, 0)
        _paths[name] = smallMap
        _paths.forEach { loopPaths2(it) }
    }

    private fun checkContainsPoint(name: String) {
        if (_paths.contains(name)) {
            throw IllegalArgumentException("$name 은 존재하는 포인트입니다.")
        }
    }

    private fun loopPaths2(path: Map.Entry<String, MutableMap<String, Path>>) {
        _paths.forEach { setPath(!path.value.contains(it.key), listOf(path, it)) }
    }

    private fun setPath(boolean: Boolean, list: List<Map.Entry<String, MutableMap<String, Path>>>) {
        if (boolean) {
            val path1 = list.first()
            val path2 = list.last()
            path1.value[path2.key] = Path(listOf(path1.key, path2.key), INF, INF)
        }
    }

    fun setBetweenValue(point1: String, point2: String, totalValue: List<Int>) {
        checkSamePoint(point1, point2)
        val path1 = getPathsIt(point1)[point2]
        val path2 = getPathsIt(point2)[point1]
        updatePath(path1, totalValue)
        updatePath(path2, totalValue)
    }

    private fun checkSamePoint(point1: String, point2: String) {
        if (point1 == point2) {
            throw java.lang.IllegalArgumentException("$point1 과 $point2 은 같은 포인트입니다.")
        }
    }

    private fun getPathsIt(point: String): MutableMap<String, Path> {
        return _paths[point] ?: throw IllegalArgumentException("$point 은 존재하지 않는 포인트입니다.")
    }

    private fun updatePath(path: Path?, totalValue: List<Int>) {
        val mainValue = totalValue.first()
        val subValue = totalValue.last()
        path!!.updateMain(mainValue)
        path.updateSub(subValue)
    }

    fun getPaths(): Map<String, Map<String, Path>> = _paths.toMap()

    fun addVisitPoint(point: String) {
        _visitPoint.add(point)
    }

    fun getPathsItToMap(point: String): Map<String, Path> {
        return _paths[point] ?: throw IllegalArgumentException("$point 은 존재하지 않는 포인트입니다.")
    }

    fun getMinPoint(startPoint: String, paths: Map<String, Path>): String {
        val minPoint = MinPoint(startPoint, _visitPoint)
        val pointName = minPoint.get(paths)
        addVisitPoint(pointName)
        return pointName
    }

    fun setStartPoint(point: String) {
        _points["출발지점"] = point
    }

    fun setArrivalPoint(point: String) {
        _points["도착지점"] = point
    }

    fun getStartPoint(): String {
        return _points.getValue("출발지점")
    }

    fun getArrivalPoint(): String {
        return _points.getValue("도착지점")
    }

    fun forEach(point: String, updatePath: (path: Map.Entry<String, Path>) -> Unit) {
        val paths = getPathsItToMap(point)
        paths.forEach { updatePath(it) }
    }

    companion object {
        const val INF: Int = 1000000000
    }
}
