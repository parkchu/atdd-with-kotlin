package nextstep.subway.path.fastest.domain

import nextstep.subway.line.domain.LineStation
import nextstep.subway.station.domain.Station
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FastestPathAppTest {
    @Test
    fun findFastestPath() {
        val stations = listOf(
                Station("1", 1),
                Station("2", 2),
                Station("3", 3),
                Station("4", 4)
        )
        val lineStations = listOf(
                LineStation(1, null, 0, 0),
                LineStation(2, 1, 10, 10, intervalTime = 7),
                LineStation(3, 2, 10, 10, intervalTime = 7),
                LineStation(4, 3, 10, 10, intervalTime = 7)
        ) // 9시에서 18시로 설정되어 있습니다. 추가요금은 0원입니다.
        val pathApp = FastestPathApp(lineStations, stations)
        val time = "1500"

        val path = pathApp.getFastestPath(1, 4, time)

        assertThat(path.getDistance()).isEqualTo(30)
        assertThat(path.getDuration(time)).isEqualTo(42)
    }

    @Test
    fun findFastestPath2() {
        val stations = listOf(
                Station("1", 1),
                Station("2", 2),
                Station("3", 3),
                Station("4", 4)
        )
        val lineStations = listOf(
                LineStation(1, null, 0, 0),
                LineStation(2, 1, 10, 10, intervalTime = 7),
                LineStation(3, 2, 10, 10, intervalTime = 7),
                LineStation(4, 3, 10, 10, intervalTime = 7)
        ) // 9시에서 18시로 설정되어 있습니다. 추가요금은 0원입니다.
        val pathApp = FastestPathApp(lineStations, stations)
        val time = "1500"

        val path = pathApp.getFastestPath(4, 1, time)

        assertThat(path.getDistance()).isEqualTo(30)
        assertThat(path.getDuration(time)).isEqualTo(42)
    }
}
