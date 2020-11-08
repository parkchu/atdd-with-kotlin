package nextstep.subway.path.application

import nextstep.subway.line.domain.LineStationRepository
import nextstep.subway.path.domain.PathApp
import nextstep.subway.path.dto.PathResponse
import nextstep.subway.station.domain.StationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PathService @Autowired constructor(
        val lineStationRepository: LineStationRepository,
        val stationRepository: StationRepository
) {
    fun findShortest(startStationId: Long, arrivalStationId: Long, type: String): PathResponse {
        val pathApp = PathApp(lineStationRepository.findAll(), stationRepository.findAll())
        return PathResponse.of(pathApp.getShortestPath(startStationId, arrivalStationId, type))
    }
}
