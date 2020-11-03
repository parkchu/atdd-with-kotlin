package nextstep.subway.path.application

import nextstep.subway.line.domain.LineStationRepository
import nextstep.subway.path.dto.PathResponse
import nextstep.subway.station.domain.StationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PathService @Autowired constructor(
        private val lineStationRepository: LineStationRepository,
        private val stationRepository: StationRepository
) {
    private val lineStations = lineStationRepository.findAll()
}
