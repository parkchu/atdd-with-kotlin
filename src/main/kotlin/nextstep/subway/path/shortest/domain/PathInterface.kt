package nextstep.subway.path.shortest.domain

interface PathInterface {
    fun getShortestPath(paths: Paths): Map<String, List<String>>
}
