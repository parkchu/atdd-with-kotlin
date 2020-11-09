package nextstep.subway.path.domain

import nextstep.subway.line.domain.LineStation
import nextstep.subway.station.domain.Station

class NewPathApp(val lineStations: List<LineStation>, val stations: List<Station>) : PathInterface {
    private val _paths: MutableMap<Long, MutableMap<Long, Path>> = mutableMapOf()
    private val _visitStation: MutableList<Long> = mutableListOf()

    init {
        stations.forEach { _paths[it.id] = makeMap(it.id) }
    }

    private fun makeMap(stationId: Long): MutableMap<Long, Path> {
        val smallMap = mutableMapOf<Long, Path>()
        stations.forEach { smallMap[it.id] = getPath(stationId, it.id) }
        return smallMap
    }

    private fun getPath(stationId: Long, stationId2: Long): Path {
        if (stationId == stationId2) {
            return Path(listOf())
        }
        val pathStation = PathStation(stations.find { it.id == stationId }
                ?: throw RuntimeException("해당역은 없어요"), LineStation(stationId, null, 0, 0), null)
        val lineStation = filterLineStations(stationId, stationId2).minBy { it.distance }
                ?: return Path(listOf(
                        pathStation,
                        PathStation(Station("없어용"), LineStation(0, null, INF, INF), null)))
        return Path(listOf(
                pathStation,
                PathStation(stations.find { it.id == stationId2 }
                        ?: throw RuntimeException("해당역은 없어요"), lineStation, null)))
    }

    private fun filterLineStations(stationId: Long, stationId2: Long): List<LineStation> {
        return lineStations.filter { checkConnect(it, stationId, stationId2) }
    }

    private fun checkConnect(lineStation: LineStation, stationId: Long, stationId2: Long): Boolean {
        return (lineStation.stationId == stationId && lineStation.preStationId == stationId2) || (lineStation.stationId == stationId2 && lineStation.preStationId == stationId)
    }

    override fun getShortestPath(startStationId: Long, arrivalStationId: Long, type: String): Path {
        dijkstra(startStationId)
        return getAllPath(startStationId)[arrivalStationId]
                ?: throw RuntimeException("도착역이 존재하지 않습니다.")
    }

    private fun dijkstra(startStationId: Long) {
        _visitStation.add(startStationId)
        val paths = getAllPath(startStationId)
        repeat(_paths.size - 1) {
            val stationId = getMinStationId(startStationId, paths)
            _visitStation.add(stationId)
            val paths2 = getAllPath(stationId)
            paths2.forEach {
                val path = paths.getValue(stationId)
                if (path.distance + it.value.distance < paths.getValue(it.key).distance) {
                    paths[it.key] = path.add(it.value)
                }
            }
        }
    }

    private fun getMinStationId(startStationId: Long, paths: Map<Long, Path>): Long {
        var distance: Int = INF
        var stationId: Long = startStationId
        paths.forEach {
            if (it.value.distance < distance && !_visitStation.contains(it.key)) {
                distance = it.value.distance
                stationId = it.key
            }
        }
        return stationId
    }

    private fun getAllPath(stationId: Long): MutableMap<Long, Path> {
        return _paths[stationId] ?: throw RuntimeException("해당 역은 존재하지 않는다.")
    }

    companion object {
        const val INF: Int = 1000000000
    }
}
