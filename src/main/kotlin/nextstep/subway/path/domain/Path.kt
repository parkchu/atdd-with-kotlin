package nextstep.subway.path.domain

import nextstep.subway.line.domain.LineStation
import nextstep.subway.path.dto.PathStationResponse
import nextstep.subway.station.domain.Station

class Path(val stations: List<LineStation>) {
    val distance: Int
        get() = stations.sumBy { it.distance }
    val duration: Int
        get() = stations.sumBy { it.duration }

    fun getStationsResponse(stations: List<Station>): List<PathStationResponse> {
        return this.stations.map {
            PathStationResponse.of(stations.find { station -> station.id == it.stationId } ?: throw RuntimeException())
        }
    }
}
