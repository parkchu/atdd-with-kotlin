package nextstep.subway.path.domain

interface PathInterface {
    fun getShortestPath(paths: Paths, type: String): Map<String, List<String>>
}
