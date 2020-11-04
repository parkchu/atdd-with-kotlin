package nextstep.subway.path.application

import nextstep.subway.line.domain.LineStation
import nextstep.subway.line.domain.LineStationRepository
import nextstep.subway.path.domain.Path
import nextstep.subway.path.domain.PathStation
import nextstep.subway.path.domain.PathStations
import nextstep.subway.path.domain.Paths
import nextstep.subway.path.dto.PathResponse
import nextstep.subway.station.domain.Station
import nextstep.subway.station.domain.StationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PathService @Autowired constructor(
        val lineStationRepository: LineStationRepository,
        val stationRepository: StationRepository
) {
    private var lineStations: List<LineStation> = listOf()
    private var stations: List<Station> = listOf()
    private val pathStations = PathStations()
    private val paths = Paths()

    fun findShortest(startStationId: Long, arrivalStationId: Long, type: String): PathResponse {
        init()
        checkCorrectValues(startStationId, arrivalStationId)
        addStartStation(startStationId)
        makePaths(arrivalStationId, startStationId)
        return PathResponse.of(paths.getShortestPath(type))
    }

    private fun init() {
        lineStations = lineStationRepository.findAll()
        stations = stationRepository.findAll()
        paths.init()
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
                    paths.add(Path(pathStations.pathStations))
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
