package nextstep.subway.path.domain

class Paths {
    private val _paths = mutableListOf<Path>()

    fun add(path: Path) {
        _paths.add(path)
    }

    fun getShortestPath(): Path {
        return _paths.minBy { it.distance } ?: throw RuntimeException("출발역과 도착역을 이어주는 경로가 하나도 존재하지 않을경우의 예외처리")
    }

    fun init() {
        _paths.clear()
    }
}
