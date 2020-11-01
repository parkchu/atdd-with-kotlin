package nextstep.subway.path.domain

import nextstep.subway.line.domain.LineStation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PathStationsTest {

    @Test
    fun makePath() {
        val stations = PathStations()

        assertThat(stations.stations).isEmpty()
    }

    @Test
    fun addStation() {
        val stations = PathStations()
        val lineStation = LineStation(1, 1, 5, 10)

        stations.add(lineStation)

        assertThat(stations.stations.first()).isEqualTo(lineStation)
    }

    @Test
    fun addSameLineStation() {
        val stations = PathStations()
        val lineStation = LineStation(1, null, 5, 10)
        val lineStation2 = LineStation(2, 1, 5, 10)
        val lineStation3 = LineStation(3, 2, 5, 10)
        stations.add(lineStation)
        stations.add(lineStation2)
        stations.add(lineStation3)
        assertThat(stations.stations.last()).isEqualTo(lineStation3)
        assertThat(stations.stations).hasSize(3)

        stations.add(lineStation2)

        assertThat(stations.stations.last()).isEqualTo(lineStation2)
        assertThat(stations.stations).hasSize(2)
    }

    @Test
    fun addSameStationId() {
        val stations = PathStations()
        val lineStation = LineStation(1, null, 5, 10)
        val lineStation2 = LineStation(2, 1, 5, 10)
        val lineStation3 = LineStation(3, 2, 5, 10)
        stations.add(lineStation)
        stations.add(lineStation2)
        stations.add(lineStation3)
        assertThat(stations.stations.last()).isEqualTo(lineStation3)
        assertThat(stations.stations).hasSize(3)

        val lineStation4 = LineStation(2, null, 10, 20)
        stations.add(lineStation4)

        assertThat(stations.stations.last()).isEqualTo(lineStation4)
        assertThat(stations.stations).hasSize(2)
    }

    @Test
    fun addStartStation() {
        val stations = PathStations()
        val lineStation = LineStation(1, null, 5, 10)
        val lineStation2 = LineStation(2, 1, 5, 10)
        val lineStation3 = LineStation(3, 2, 5, 10)
        stations.add(lineStation)
        stations.add(lineStation2)
        stations.add(lineStation3)
        assertThat(stations.stations.last()).isEqualTo(lineStation3)
        assertThat(stations.stations).hasSize(3)

        val lineStation4 = LineStation(1, 4, 10, 30)
        stations.add(lineStation4)

        assertThat(stations.stations.last()).isEqualTo(lineStation4)
        assertThat(stations.stations).hasSize(1)
    }
}
