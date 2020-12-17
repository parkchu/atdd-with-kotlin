package nextstep.subway.path.fastest.domain

import nextstep.subway.line.domain.LineStation
import nextstep.subway.station.domain.Station

class FastestPathApp(val lineStations: List<LineStation>, val stations: List<Station>) {

    fun getFastestPath(stationIds: List<Long>, time: String): FastestPath {
        val startStationId = stationIds.first()
        val arrivalStationId = stationIds.last()
        checkCorrectValues(startStationId, arrivalStationId)
        val pathStations = PathStations()
        pathStations.add(addStartStation(startStationId))
        val paths = makePaths(stationIds, pathStations)
        return paths.getFastestPath(time)
    }

    private fun checkCorrectValues(startStationId: Long, arrivalStationId: Long) {
        if (startStationId == arrivalStationId) {
            throw RuntimeException("출발역과 도착역이 같습니다")
        }
        stations.find { it.id == arrivalStationId } ?: throw RuntimeException("도착역이 존재하지 않는 역일 경우")
    }

    private fun addStartStation(startStationId: Long): PathStation {
        val station = stations.find { it.id == startStationId } ?: throw RuntimeException("출발역이 존재하지 않는 역일 경우")
        return PathStation(station, LineStation(startStationId, null, 0, 0), null)
    }

    private fun makePaths(stationIds: List<Long>, pathStations: PathStations, paths: Paths = Paths()): Paths {
        val beforeStationId = stationIds.first()
        val arrivalStationId = stationIds.last()
        val stations = pathStations.removeStations(stations.toMutableList())
        stations.forEach { station ->
            val filterLineStations = this.lineStations.filter { checkConnect(it, station.id, beforeStationId) }
            filterLineStations.forEach { lineStation ->
                pathStations.add(PathStation(station, lineStation, beforeStationId))
                if (station.id == arrivalStationId) {
                    paths.add(FastestPath(pathStations.pathStations))
                } else {
                    makePaths(listOf(station.id, arrivalStationId), pathStations, paths)
                }
            }
        }
        return paths
    }

    private fun checkConnect(lineStation: LineStation, stationId: Long, beforeStationId: Long): Boolean {
        return (lineStation.stationId == stationId && lineStation.preStationId == beforeStationId) || (lineStation.stationId == beforeStationId && lineStation.preStationId == stationId)
    }
}
