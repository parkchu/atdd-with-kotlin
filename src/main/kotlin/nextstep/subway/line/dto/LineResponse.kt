package nextstep.subway.line.dto

import nextstep.subway.line.domain.Line
import java.time.LocalDateTime
import java.time.LocalTime

data class LineResponse(
        val id: Long,
        val name: String,
        val color: String,
        val startTime: LocalTime,
        val endTime: LocalTime,
        val intervalTime: Int,
        val stations: List<LineStationResponse>,
        val createdDate: LocalDateTime?,
        val modifiedDate: LocalDateTime?
) {
    companion object {
        fun of(line: Line, stations: List<LineStationResponse> = listOf()): LineResponse {
            return LineResponse(
                    line.id,
                    line.name,
                    line.color,
                    line.startTime,
                    line.endTime,
                    line.intervalTime,
                    stations,
                    line.createdDate,
                    line.updatedDate)
        }
    }
}
