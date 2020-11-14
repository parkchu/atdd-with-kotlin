package nextstep.subway.path.domain

interface PathInterface {
    fun getShortestPath(paths: Paths): Map<String, List<String>>
}
