package nextstep.subway.line.domain

import nextstep.subway.path.shortest.domain.Paths.Companion.INF
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

        var reverseStartTime: LocalTime = LocalTime.of(9, 0),

        var endTime: LocalTime = LocalTime.of(18, 0),

        var reverseEndTime: LocalTime = LocalTime.of(18, 0),

        var intervalTime: Int = 1,
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
        reverseStartTime = line.startTime
        endTime = line.endTime
        reverseEndTime = line.endTime
        intervalTime = line.intervalTime
    }

    fun getDuration(time: LocalTime, isReverse: Boolean): Int {
        val currentTime = changeMinute(time)
        val startTime: Int
        val endTime: Int
        if (isReverse) {
            startTime = changeMinute(reverseStartTime)
            endTime = changeMinute(reverseEndTime)
        } else {
            startTime = changeMinute(this.startTime)
            endTime = changeMinute(this.endTime)
        }
        if (currentTime in startTime..endTime) {
            return test2(currentTime, startTime)
        }
        return INF
    }

    private fun changeMinute(localTime: LocalTime): Int {
        val hour = localTime.hour
        val minute = localTime.minute
        return hour * 60 + minute
    }

    private fun test2(currentTime: Int, startTime: Int): Int {
        checkZero()
        val remainder = (currentTime - startTime) % intervalTime
        return if (remainder == 0) {
            duration
        } else {
            duration + intervalTime - remainder
        }
    }

    private fun checkZero() {
        if (intervalTime == 0) {
            intervalTime = 1
        }
    }
}
