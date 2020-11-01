package nextstep.subway.path.domain

class Paths() {
    private val _paths = mutableListOf<Path>()
    val paths: List<Path>
        get() = _paths.toList()

    fun add(path: Path) {
        _paths.add(path)
    }

    fun getShortestPath(): Path {
        return paths.minBy { it.distance } ?: Path()
    }
}
