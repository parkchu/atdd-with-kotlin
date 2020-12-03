package nextstep.subway.path.fastest.domain

import nextstep.subway.line.domain.LineStation
import nextstep.subway.station.domain.Station

class FastestPathApp(val lineStations: List<LineStation>, val stations: List<Station>) {
    private val pathStations = PathStations()
    private val paths = Paths()

    fun getFastestPath(startStationId: Long, arrivalStationId: Long, time: String): FastestPath {
        checkCorrectValues(startStationId, arrivalStationId)
        addStartStation(startStationId)
        makePaths(arrivalStationId, startStationId)
        return paths.getFastestPath(time)
    }

    private fun checkCorrectValues(startStationId: Long, arrivalStationId: Long) {
        if (startStationId == arrivalStationId) {
            throw RuntimeException("출발역과 도착역이 같습니다")
        }
        stations.find { it.id == arrivalStationId } ?: throw RuntimeException("도착역이 존재하지 않는 역일 경우")
    }

    private fun addStartStation(startStationId: Long) {
        val station = stations.find { it.id == startStationId } ?: throw RuntimeException("출발역이 존재하지 않는 역일 경우")
        pathStations.add(PathStation(station, LineStation(startStationId, null, 0, 0), null))
    }

    private fun makePaths(arrivalStationId: Long, beforeStationId: Long) {
        val stations = removeStations()
        stations.forEach { station ->
            val filterLineStations = this.lineStations.filter { checkConnect(it, station.id, beforeStationId) }
            filterLineStations.forEach { lineStation ->
                pathStations.add(PathStation(station, lineStation, beforeStationId))
                if (station.id == arrivalStationId) {
                    paths.add(FastestPath(pathStations.pathStations))
                } else {
                    makePaths(arrivalStationId, station.id)
                }
            }
        }
    }

    private fun removeStations(): List<Station> {
        val stations = this.stations.toMutableList()
        stations.removeAll(pathStations.getStations())
        return stations
    }

    private fun checkConnect(lineStation: LineStation, stationId: Long, beforeStationId: Long): Boolean {
        return (lineStation.stationId == stationId && lineStation.preStationId == beforeStationId) || (lineStation.stationId == beforeStationId && lineStation.preStationId == stationId)
    }
}
