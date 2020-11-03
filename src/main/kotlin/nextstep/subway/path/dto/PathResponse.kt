package nextstep.subway.path.dto

import nextstep.subway.path.domain.Path

class PathResponse(
        val stations: List<PathStationResponse>,
        val distance: Int,
        val duration: Int
) {
    companion object {
        fun of(path: Path): PathResponse {
            return PathResponse(
                    path.getStationsResponse(),
                    path.distance,
                    path.duration)
        }
    }
}
