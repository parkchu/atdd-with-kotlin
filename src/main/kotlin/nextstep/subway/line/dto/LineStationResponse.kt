package nextstep.subway.line.dto

import nextstep.subway.station.dto.StationResponse

data class LineStationResponse(val station: StationResponse, val preStationId: Long, val distance: Int, val duration: Int)
