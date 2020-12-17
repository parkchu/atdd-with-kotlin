package nextstep.subway.path.fastest.domain

class Paths {
    private val _paths = mutableListOf<FastestPath>()

    fun add(path: FastestPath) {
        _paths.add(path)
    }

    fun getFastestPath(time: String): FastestPath {
        return _paths.minBy { it.getDuration(time) } ?: FastestPath(listOf())
    }
}
