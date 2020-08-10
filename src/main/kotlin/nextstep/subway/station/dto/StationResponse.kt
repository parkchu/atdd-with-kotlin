package nextstep.subway.station.dto

import nextstep.subway.station.domain.Station
import java.time.LocalDateTime

class StationResponse(
        val id: Long,
        val name: String,
        val createdDate: LocalDateTime?,
        val modifiedDate: LocalDateTime?
) {
    companion object {
        fun of(station: Station): StationResponse {
            return StationResponse(station.id, station.name, station.createdDate, station.updatedDate)
        }
    }
}
