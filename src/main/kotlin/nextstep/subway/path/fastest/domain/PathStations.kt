package nextstep.subway.path.fastest.domain

import nextstep.subway.station.domain.Station

class PathStations {
    private val _pathStations = mutableListOf<PathStation>()
    val pathStations: List<PathStation>
        get() = _pathStations.toList()

    fun add(pathStation: PathStation) {
        _pathStations.add(relocation(pathStation))
    }

    private fun relocation(pathStation: PathStation): PathStation {
        val index = _pathStations.indexOf(_pathStations.find { it.beforeStationId == pathStation.beforeStationId } ?: return pathStation)
        _pathStations.retainAll(_pathStations.take(index))
        return pathStation
    }

    fun removeStations(stations: MutableList<Station>): List<Station> {
        stations.removeAll(getStations())
        return stations
    }

    private fun getStations(): List<Station> {
        return _pathStations.map { it.station }
    }
}
