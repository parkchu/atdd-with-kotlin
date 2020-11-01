package nextstep.subway.path.domain

import nextstep.subway.line.domain.LineStation

class PathStations {
    private val _stations: MutableList<LineStation> = mutableListOf()
    val stations: List<LineStation>
        get() = _stations.toList()

    fun add(lineStation: LineStation) {
        _stations.add(relocationStations(lineStation))
    }

    private fun relocationStations(lineStation: LineStation): LineStation {
        val index = _stations.indexOf(_stations.find { it.stationId == lineStation.stationId } ?: return lineStation)
        _stations.retainAll(_stations.take(index))
        return lineStation
    }
}
