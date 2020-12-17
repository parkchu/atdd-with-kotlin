package nextstep.subway.path.shortest.domain

class MinPoint(private var _pointName: String, private val _visitPoint: List<String>) {
    private var _betweenValue: Int = Paths.INF

    fun get(paths: Map<String, Path>): String {
        paths.forEach { setMinPoint(it) }
        return _pointName
    }

    private fun setMinPoint(pathMap: Map.Entry<String, Path>) {
        if (pathMap.value.mainValue < _betweenValue && !_visitPoint.contains(pathMap.key)) {
            _betweenValue = pathMap.value.mainValue
            _pointName = pathMap.key
        }
    }
}
