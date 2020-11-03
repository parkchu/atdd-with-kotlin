package nextstep.subway.path.domain

class PathStations {
    private val _stations = mutableListOf<PathStation>()
    val stations: List<PathStation>
        get() = _stations.toList()

    fun add(pathStation: PathStation) {
        _stations.add(relocation(pathStation))
    }

    private fun relocation(pathStation: PathStation): PathStation {
        val index = _stations.indexOf(_stations.find { it.beforeStationId == pathStation.beforeStationId } ?: return pathStation)
        _stations.retainAll(_stations.take(index))
        return pathStation
    }

}
