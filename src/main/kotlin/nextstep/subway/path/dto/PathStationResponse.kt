package nextstep.subway.path.dto

import nextstep.subway.station.domain.Station
import java.time.LocalDateTime

class PathStationResponse(
        val id: Long,
        val name: String,
        val createdAt: LocalDateTime?
) {
    companion object {
        fun of(station: Station): PathStationResponse {
            return PathStationResponse(station.id, station.name, station.createdDate)
        }
    }
}
