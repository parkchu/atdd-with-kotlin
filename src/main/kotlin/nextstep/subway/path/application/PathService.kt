package nextstep.subway.path.application

import nextstep.subway.line.domain.LineStation
import nextstep.subway.line.domain.LineStationRepository
import nextstep.subway.path.domain.*
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
        val path = pathApp.getShortestPath(paths)
        val totalValue = TotalValue((path["총"] ?: error("")).map { it.toInt() }).get(type)
        val extraFare = totalValue[1]
        return PathResponse.of(
                (path["경로"] ?: error("")).map { PathStationResponse.of(getStationByName(it)) },
                totalValue,
                extraFare
        )
    }

    private fun init(type: String) {
        paths.clearPaths()
        val stations = PathStations(stationRepository.findAll())
        stations.forEach { paths.setPoint(it.name) }
        stations.loopStationsNotEmpty { setBetweenValue(it, type) }
    }

    private fun setBetweenValue(stations: List<Station>, type: String) {
        val lineStations = lineStationRepository.findAll()
        val pathLineStations = PathLineStations(lineStations.filter { it.checkConnect(stations.first().id, stations.last().id) })
        val lineStation = pathLineStations.getMinLineStation(type)
        pathsSetBetweenValue(lineStation, stations, type)
    }

    private fun pathsSetBetweenValue(lineStation: LineStation?, stations: List<Station>, type: String) {
        if (lineStation != null) {
            val totalValue = TotalValue(listOf(lineStation.distance, lineStation.extraFare, lineStation.duration))
            paths.setBetweenValue(stations.first().name, stations.last().name, totalValue.get(type))
        }
    }

    private fun getStationName(stationId: Long): String {
        return stationRepository.findById(stationId).orElseThrow { IllegalArgumentException("해당 역은 존재하지 않는다.") }.name
    }

    private fun getStationByName(name: String): Station {
        return stationRepository.findByName(name) ?: throw IllegalArgumentException("해당 역은 존재하지 않는다.")
    }
}
