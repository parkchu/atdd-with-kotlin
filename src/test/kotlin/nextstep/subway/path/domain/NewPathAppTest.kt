package nextstep.subway.path.domain

import nextstep.subway.line.domain.LineStation
import nextstep.subway.station.domain.Station
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class NewPathAppTest {
    @Test
    fun initApp() {
        val lineStations = listOf(
                LineStation(2, 1, 5, 10),
                LineStation(3, 1, 6, 10),
                LineStation(4, 2, 10, 10)
        )
        val stations = listOf(
                Station("1", id = 1),
                Station("2", id = 2),
                Station("3", id = 3),
                Station("4", id = 4)
        )

        val pathApp = NewPathApp(lineStations, stations)
        val firstId = stations.first().id
        assertThat(pathApp.getShortestPath(firstId, firstId, "DISTANCE").distance).isEqualTo(0)
        assertThat(pathApp.getShortestPath(firstId, stations[1].id, "DISTANCE").distance).isEqualTo(5)
        assertThat(pathApp.getShortestPath(firstId, stations[2].id, "DISTANCE").distance).isEqualTo(6)
        assertThat(pathApp.getShortestPath(firstId, stations.last().id, "DISTANCE").distance).isEqualTo(15)
        assertThat(pathApp.getShortestPath(2, firstId, "DISTANCE").distance).isEqualTo(5)
    }
}
