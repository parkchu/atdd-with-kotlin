package nextstep.subway.path.domain

interface PathInterface {
    fun getShortestPath(startStationId: Long, arrivalStationId: Long, type: String): Path
}
