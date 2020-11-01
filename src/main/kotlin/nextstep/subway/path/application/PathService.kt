package nextstep.subway.path.application

import nextstep.subway.line.domain.LineStation
import nextstep.subway.line.domain.LineStationRepository
import nextstep.subway.path.domain.Path
import nextstep.subway.path.domain.PathStations
import nextstep.subway.path.dto.PathResponse
import nextstep.subway.station.domain.StationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PathService @Autowired constructor(
        private val lineStationRepository: LineStationRepository,
        private val stationRepository: StationRepository
) {
    private val lineStations
        get() = lineStationRepository.findAll()

    fun findShortest(startStationId: Long, arrivalStationId: Long): PathResponse {
        val path = PathStations()
        path.add(findByStationId(startStationId))
        path.add(findByStationId(arrivalStationId))
        return PathResponse.of(Path(listOf()), stationRepository.findAll())
    }

    private fun findByStationId(stationId: Long): LineStation {
        return lineStations.find { it.stationId == stationId } ?: throw RuntimeException()
    }
}
