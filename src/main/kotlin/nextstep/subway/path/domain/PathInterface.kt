package nextstep.subway.path.domain

interface PathInterface {
    fun getShortestPath(startPoint: String, arrivalPoint: String): Map<String, List<String>>
}
