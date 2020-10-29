package nextstep.subway.path.dto

import nextstep.subway.path.domain.Path
import nextstep.subway.station.dto.StationResponse

class PathResponse(
        val stations: List<StationResponse>,
        val distance: Int,
        val duration: Int
) {
    companion object {
        fun of(path: Path): PathResponse {
            return PathResponse(path.stations, path.distance, path.duration)
        }
    }
}
