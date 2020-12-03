package nextstep.subway.line.domain

import nextstep.subway.path.shortest.domain.Paths.Companion.INF
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalTime

class LineStationTest {
    @Test
    fun getDuration() {
        val lineStation = LineStation(0, 0, 0, 10, 0, intervalTime = 10) // 출발시간은 9시 도착시간은 18시로 설정되어있다.
        val time = "1500"

        val duration = lineStation.getDuration(changeLocalTime(time))

        assertThat(duration).isEqualTo(10)
    }

    @Test
    fun getDuration2() {
        val lineStation = LineStation(0, 0, 0, 10, 0, intervalTime = 7) // 출발시간은 9시 도착시간은 18시로 설정되어있다.
        val time = "1500"

        val duration = lineStation.getDuration(changeLocalTime(time))

        assertThat(duration).isEqualTo(14)
    }

    @Test
    fun getDuration3() {
        val lineStation = LineStation(0, 0, 0, 10, 0, intervalTime = 7) // 출발시간은 9시 도착시간은 18시로 설정되어있다.
        val time = "1900"

        val duration = lineStation.getDuration(changeLocalTime(time))

        assertThat(duration).isEqualTo(INF)
    }

    private fun changeLocalTime(time: String): LocalTime {
        val length = time.length
        val hourRange = length - 4 until length - 2
        val minuteRange = length - 2 until length
        val hour = time.slice(hourRange).toInt()
        val minute = time.slice(minuteRange).toInt()
        return LocalTime.of(hour, minute)
    }
}
