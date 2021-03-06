package nextstep.subway.line.domain

import nextstep.subway.core.domain.BaseEntity
import nextstep.subway.line.dto.LineStationResponse
import nextstep.subway.station.domain.Station
import java.time.LocalTime
import javax.persistence.*

@Entity
@Table(name = "line")
class Line(
        @Column(unique = true, nullable = false)
        var name: String,

        var color: String,

        var startTime: LocalTime,

        var endTime: LocalTime,

        var intervalTime: Int,

        var extraFare: Int = 0,

        @Embedded
        var lineStations: LineStations = LineStations(),

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0
) : BaseEntity() {
    fun update(updateLine: Line) {
        this.name = updateLine.name
        this.color = updateLine.color
        this.startTime = updateLine.startTime
        this.endTime = updateLine.endTime
        this.intervalTime = updateLine.intervalTime
        this.extraFare = updateLine.extraFare
        this.lineStations.update(updateLine)
    }

    fun getLineStationResponse(stations: List<Station>): List<LineStationResponse> {
        return lineStations.getLineStationResponses(stations)
    }

    fun addStation(lineStation: LineStation) {
        lineStations.add(lineStation)
    }

    fun deleteStation(stationId: Long) {
        lineStations.delete(stationId)
    }
}
