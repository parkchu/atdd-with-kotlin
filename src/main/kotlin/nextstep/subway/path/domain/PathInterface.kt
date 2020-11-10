package nextstep.subway.path.domain

interface PathInterface {
    fun getShortestPath(paths: Paths, startPoint: String, arrivalPoint: String): Map<String, List<String>>
}
