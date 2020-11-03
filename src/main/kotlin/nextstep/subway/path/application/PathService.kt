package nextstep.subway.path.application

import nextstep.subway.line.domain.LineStationRepository
import nextstep.subway.path.domain.PathStations
import nextstep.subway.path.domain.Paths
import nextstep.subway.path.dto.PathResponse
import nextstep.subway.station.domain.Station
import nextstep.subway.station.domain.StationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PathService @Autowired constructor(
        lineStationRepository: LineStationRepository,
        stationRepository: StationRepository
) {
    private val lineStations = lineStationRepository.findAll()
    private val stations = stationRepository.findAll().toMutableList()
    private val pathStations = PathStations()
    private val paths = Paths()

    fun findShortest(startStationId: Long, arrivalStationId: Long): PathResponse {
        makePaths(startStationId, arrivalStationId)
        return PathResponse.of(paths.getShortestPath())
    }

    private fun makePaths(startStationId: Long, arrivalStationId: Long, beforeStationId: Long? = null) {
        val stations = removeStations()

    }

    private fun removeStations(): List<Station> {
        val stations = this.stations
        stations.removeAll(pathStations.getStations())
        return stations
    }
}
