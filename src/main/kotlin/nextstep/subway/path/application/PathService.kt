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

    fun findShortest(startStationId: Long, arrivalStationId: Long, type: String): PathResponse {
        init()
        val startStation = getStation(startStationId)
        val arrivalStation = getStation(arrivalStationId)
        val path = pathApp.getShortestPath(paths, startStation.name, arrivalStation.name)
        return PathResponse.of(
                (path["경로"] ?: error("")).map { PathStationResponse.of(getStationOfName(it)) },
                (path["총"] ?: error("")).map { it.toInt() }
        )
    }

    private fun init() {
        paths.init()
        val lineStations = lineStationRepository.findAll()
        val stations = stationRepository.findAll()
        stations.forEach { paths.setPoint(it.name) }
        val stations2 = stations.toMutableList()
        while (stations2.isNotEmpty()) {
            val station = stations2.first()
            stations2.remove(station)
            stations2.forEach { station2 ->
                val lineStation = lineStations.filter { it.checkConnect(station.id, station2.id) }.minBy { it.distance }
                setBetweenValue(lineStation, station, station2)
            }
        }
    }

    private fun setBetweenValue(lineStation: LineStation?, station1: Station, station2: Station) {
        if (lineStation != null) {
            paths.setBetweenValue(station1.name, station2.name, lineStation.distance, lineStation.duration)
        }
    }

    private fun getStation(stationId: Long): Station {
        return stationRepository.findById(stationId).orElseThrow { IllegalArgumentException("해당 역은 존재하지 않는다.") }
    }

    private fun getStationOfName(name: String): Station {
        return stationRepository.findByName(name) ?: throw IllegalArgumentException("해당 역은 존재하지 않는다.")
    }
}
