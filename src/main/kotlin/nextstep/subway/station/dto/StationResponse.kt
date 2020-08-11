package nextstep.subway.station.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import nextstep.subway.station.domain.Station
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
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
