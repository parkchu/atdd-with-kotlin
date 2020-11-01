package nextstep.subway.path.domain

import nextstep.subway.line.domain.LineStation
import nextstep.subway.path.dto.PathStationResponse
import nextstep.subway.station.domain.Station

class Path(val startStationId: Long, val arrivalStationId: Long) {
    private val _stations: MutableList<LineStation> = mutableListOf()
    val stations: List<LineStation>
        get() = _stations.toList()
    val distance: Int
        get() = _stations.sumBy { it.distance }
    val duration: Int
        get() = _stations.sumBy { it.duration }

    fun add(lineStation: LineStation) {
        _stations.add(relocationStations(lineStation))
    }

    private fun relocationStations(lineStation: LineStation): LineStation {
        val index = _stations.indexOf(_stations.find { it.stationId == lineStation.stationId } ?: return lineStation)
        _stations.retainAll(_stations.take(index))
        return lineStation
    }

    fun getStationsResponse(stations: List<Station>): List<PathStationResponse> {
        return this._stations.map {
            PathStationResponse.of(stations.find { station -> station.id == it.stationId } ?: throw RuntimeException())
        }
    }
}
