package nextstep.subway.path.dto

import nextstep.subway.station.dto.StationResponse

class PathResponse(
        val stations: List<StationResponse>,
        val distance: Int,
        val duration: Int
)
