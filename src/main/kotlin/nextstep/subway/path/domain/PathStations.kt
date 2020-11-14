package nextstep.subway.path.domain

import nextstep.subway.station.domain.Station

class PathStations(private val _stations: List<Station>) {
    fun forEach(function: (station: Station) -> Unit) {
        _stations.forEach { function(it) }
    }

    fun loopStationsNotEmpty(function: (stationList: List<Station>) -> Unit) {
        val stations = _stations.toMutableList()
        while (stations.isNotEmpty()) {
            setBetweenValue(stations) { function(it) }
        }
    }

    private fun setBetweenValue(stations: MutableList<Station>, function: (stations: List<Station>) -> Unit) {
        val station = stations.first()
        stations.remove(station)
        stations.forEach { station2 -> function(listOf(station, station2)) }
    }
}
