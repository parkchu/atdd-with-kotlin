package nextstep.subway.line.domain

import java.time.LocalTime
import javax.persistence.*

@Entity
@Table(name = "line_station")
class LineStation(
        @Column(nullable = false)
        val stationId: Long,

        var preStationId: Long? = null,

        var distance: Int,

        var duration: Int,

        var extraFare: Int = 0,

        var startTime: LocalTime = LocalTime.of(9, 0),

        var endTime: LocalTime = LocalTime.of(18, 0),

        var intervalTime: Int = 0,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0
) {
    fun isSame(lineStation: LineStation): Boolean {
        return this.stationId == lineStation.stationId
    }

    fun updatePreStationTo(id: Long?) {
        preStationId = id
    }

    fun checkConnect(stationId: Long, stationId2: Long): Boolean {
        return (this.stationId == stationId && this.preStationId == stationId2) || (this.stationId == stationId2 && this.preStationId == stationId)
    }

    fun update(line: Line) {
        extraFare = line.extraFare
        startTime = line.startTime
        endTime = line.endTime
        intervalTime = line.intervalTime
    }
}
