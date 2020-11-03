package nextstep.subway.path.domain

import nextstep.subway.line.domain.LineStation
import nextstep.subway.station.domain.Station

class PathStations {
    private val _stations = mutableListOf<PathStation>()
    val stations: List<PathStation>
        get() = _stations.toList()

    fun add(station: Station, lineStation: LineStation, beforeStationId: Long?) {
        val pathStation = PathStation(station, lineStation, beforeStationId)
        _stations.add(pathStation)
    }
}
