package nextstep.subway.line.dto

import nextstep.subway.line.domain.Line
import java.time.LocalTime

data class LineRequest(
        val name: String,
        val color: String,
        val startTime: LocalTime,
        val endTime: LocalTime,
        val intervalTime: Int
) {
    fun toLine(): Line {
        return Line(name, color, startTime, endTime, intervalTime)
    }
}
