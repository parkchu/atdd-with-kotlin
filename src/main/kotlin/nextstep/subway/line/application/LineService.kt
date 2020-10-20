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
                .map { line: Line -> LineResponse.of(line, line.getLineStationResponse(stationRepository.findAll())) }
    }

    @Transactional(readOnly = true)
    fun findLineById(id: Long): LineResponse {
        val line = findById(id)
        return LineResponse.of(line, line.getLineStationResponse(stationRepository.findAll()))
    }

    private fun findById(id: Long): Line {
        return lineRepository.findById(id).orElseThrow { RuntimeException() }
    }

    fun updateLine(id: Long, lineUpdateRequest: LineRequest) {
        val persistLine = findById(id)
        persistLine.update(lineUpdateRequest.toLine())
    }

    fun deleteLineById(id: Long) {
        lineRepository.deleteById(id)
    }

    fun addStation(lineId: Long, lineStationRequest: LineStationRequest) {
        stationRepository.findById(lineStationRequest.stationId).orElseThrow { DataIntegrityViolationException("") }
        val line = findById(lineId)
        line.addStation(lineStationRequest.toLineStation())
    }

    fun deleteStation(lineId: Long, stationId: Long) {
        val line = findById(lineId)
        line.deleteStation(stationId)
    }

    fun deleteStationAllLine(stationId: Long) {
        val lines = lineRepository.findByStationContains(stationId) ?: listOf()
        lines.forEach { deleteStation(it.id, stationId) }
    }
}
