package nextstep.subway.line.dto

import nextstep.subway.line.domain.Line
import nextstep.subway.line.domain.LineStation

data class LineStationRequest(
        val stationId: Long,
        val preStationId: Long?,
        val distance: Int,
        val duration: Int
) {
    fun toLineStation(line: Line): LineStation {
        return LineStation(stationId, preStationId, distance, duration, line.extraFare, line.startTime, line.endTime, line.intervalTime)
    }
}
