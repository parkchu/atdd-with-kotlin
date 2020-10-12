package nextstep.subway.line.application

import nextstep.subway.line.domain.Line
import nextstep.subway.line.domain.LineRepository
import nextstep.subway.line.dto.LineRequest
import nextstep.subway.line.dto.LineResponse
import nextstep.subway.line.dto.LineStationRequest
import nextstep.subway.station.domain.StationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException

@Service
@Transactional
class LineService @Autowired constructor(
        val lineRepository: LineRepository,
        val stationRepository: StationRepository
) {
    fun saveLine(request: LineRequest): Line {
        return lineRepository.save(request.toLine())
    }

    @Transactional(readOnly = true)
    fun findAllLines(): List<LineResponse> {
        val lines: List<Line> = lineRepository.findAll()
        return lines
                .map { line: Line? -> LineResponse.of(line!!, line.getLineStationResponse(stationRepository.findAll())) }
    }

    @Transactional(readOnly = true)
    fun findLineById(id: Long): LineResponse {
        return lineRepository.findById(id)
                .map { LineResponse.of(it, it.getLineStationResponse(stationRepository.findAll())) }
                .orElseThrow{ RuntimeException() }
    }

    fun updateLine(id: Long, lineUpdateRequest: LineRequest) {
        val persistLine = lineRepository.findById(id).orElseThrow { RuntimeException() }
        persistLine.update(lineUpdateRequest.toLine())
    }

    fun deleteLineById(id: Long) {
        lineRepository.deleteById(id)
    }

    fun addStation(lineId: Long, lineStationRequest: LineStationRequest) {
        stationRepository.findById(lineStationRequest.stationId).orElseThrow { DataIntegrityViolationException("") }
        val line = lineRepository.findById(lineId).orElseThrow { RuntimeException() }
        line.addStation(lineStationRequest.toLineStation())
    }
}
