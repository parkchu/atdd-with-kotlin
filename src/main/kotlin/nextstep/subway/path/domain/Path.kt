package nextstep.subway.path.domain

import nextstep.subway.station.dto.StationResponse

class Path(val startStationId: Long, val arrivalStationId: Long) {
    val stations: List<StationResponse> = listOf()
    val distance: Int = 0
    val duration: Int = 0
}
