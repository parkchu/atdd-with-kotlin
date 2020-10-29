package nextstep.subway.path.domain

import nextstep.subway.line.domain.LineStation
import nextstep.subway.path.dto.PathStationResponse
import nextstep.subway.station.domain.Station

class Path(val startStationId: Long, val arrivalStationId: Long) {
    private val _stations: MutableList<Long> = mutableListOf()
    val stations: List<Long>
        get() = _stations.toList()
    var distance: Int = 0
        private set
    var duration: Int = 0
        private set

    fun add(lineStation: LineStation) {
        _stations.add(lineStation.stationId)
        distance += lineStation.distance
        duration += lineStation.duration
    }

    fun getStationsResponse(stations: List<Station>): List<PathStationResponse> {
        return this.stations.map {
            PathStationResponse.of(stations.find { station -> station.id == it } ?: throw RuntimeException())
        }
    }
}
