package nextstep.subway.path.application

import nextstep.subway.line.domain.LineStation
import nextstep.subway.line.domain.LineStationRepository
import nextstep.subway.path.domain.PathApp
import nextstep.subway.path.domain.Paths
import nextstep.subway.path.dto.PathResponse
import nextstep.subway.path.dto.PathStationResponse
import nextstep.subway.station.domain.Station
import nextstep.subway.station.domain.StationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PathService @Autowired constructor(
        private val lineStationRepository: LineStationRepository,
        private val stationRepository: StationRepository
) {
    val pathApp = PathApp()
    val paths = Paths()

    fun findShortest(stationIds: List<Long>, type: String): PathResponse {
        init(type)
        paths.setStartPoint(getStationName(stationIds.first()))
        paths.setArrivalPoint(getStationName(stationIds.last()))
        val path = pathApp.getShortestPath(paths, type)
        return PathResponse.of(
                (path["경로"] ?: error("")).map { PathStationResponse.of(getStationByName(it)) },
                (path["총"] ?: error("")).map { it.toInt() }
        )
    }

    private fun init(type: String) {
        paths.clearPaths()
        val lineStations = lineStationRepository.findAll()
        val stations = stationRepository.findAll()
        stations.forEach { paths.setPoint(it.name) }
        val stations2 = stations.toMutableList()
        while (stations2.isNotEmpty()) {
            val station = stations2.first()
            stations2.remove(station)
            stations2.forEach { station2 ->
                val filterLineStations = lineStations.filter { it.checkConnect(station.id, station2.id) }
                val lineStation =
                        if (type == "DISTANCE") {
                            filterLineStations.minBy { it.distance }
                        } else {
                            filterLineStations.minBy { it.duration }
                        }
                setBetweenValue(lineStation, station.name, station2.name)
            }
        }
    }

    private fun setBetweenValue(lineStation: LineStation?, point1: String, point2: String) {
        if (lineStation != null) {
            val distanceAndDuration = listOf(lineStation.distance, lineStation.duration)
            paths.setBetweenValue(point1, point2, distanceAndDuration)
        }
    }

    private fun getStationName(stationId: Long): String {
        return stationRepository.findById(stationId).orElseThrow { IllegalArgumentException("해당 역은 존재하지 않는다.") }.name
    }

    private fun getStationByName(name: String): Station {
        return stationRepository.findByName(name) ?: throw IllegalArgumentException("해당 역은 존재하지 않는다.")
    }
}
