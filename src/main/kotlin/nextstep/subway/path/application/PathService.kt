package nextstep.subway.path.application

import nextstep.subway.line.application.LineService
import nextstep.subway.path.domain.Path
import nextstep.subway.path.dto.PathResponse
import nextstep.subway.station.domain.StationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PathService @Autowired constructor(
        private val lineService: LineService,
        private val stationRepository: StationRepository
) {
    fun findShortest(startStationId: Long, arrivalStationId: Long): PathResponse {
        val path = Path(startStationId, arrivalStationId)
        val startLines = lineService.findStationContains(startStationId)
        val arrivalLines = lineService.findStationContains(arrivalStationId)
        return PathResponse.of(path)
    }
}
