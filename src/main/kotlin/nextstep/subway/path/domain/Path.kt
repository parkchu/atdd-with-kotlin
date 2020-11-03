package nextstep.subway.path.domain

import nextstep.subway.path.dto.PathStationResponse

class Path(val stations: List<PathStation>) {
    val distance: Int
        get() = stations.sumBy { it.distance }
    val duration: Int
        get() = stations.sumBy { it.duration }

    fun getStationsResponse(): List<PathStationResponse> {
        return stations.map { PathStationResponse.of(it.station) }
    }
}
