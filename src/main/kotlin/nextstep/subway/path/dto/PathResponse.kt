package nextstep.subway.path.dto

import nextstep.subway.path.domain.Path
import nextstep.subway.station.domain.Station

class PathResponse(
        val stations: List<PathStationResponse>,
        val distance: Int,
        val duration: Int
) {
    companion object {
        fun of(path: Path, stations: List<Station>): PathResponse {
            return PathResponse(
                    path.getStationsResponse(stations),
                    path.distance,
                    path.duration)
        }
    }
}
