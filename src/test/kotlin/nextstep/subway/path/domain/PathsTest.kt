package nextstep.subway.path.domain

import nextstep.subway.line.domain.LineStation
import nextstep.subway.station.domain.Station
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PathsTest {
    @Test
    fun getShortestPath() {
        val stations = listOf(Station("주한역"), Station("테스트역"))
        val lineStations = listOf(LineStation(1, null, 10, 10), LineStation(2, 1, 10, 10))
        val pathStations = listOf(PathStation(stations[0], lineStations[0], null), PathStation(stations[1], lineStations[1], 1))
        val path = Path(pathStations)
        val lineStations2 = listOf(LineStation(1, null, 5, 10), LineStation(2, 1, 10, 10))
        val pathStations2 = listOf(PathStation(stations[0], lineStations2[0], null), PathStation(stations[1], lineStations2[1], 1))
        val path2 = Path(pathStations2)
        val paths = Paths()
        paths.add(path)
        paths.add(path2)

        val shortestPath = paths.getShortestPath("DISTANCE")

        assertThat(shortestPath).isEqualTo(path2)
    }
}
