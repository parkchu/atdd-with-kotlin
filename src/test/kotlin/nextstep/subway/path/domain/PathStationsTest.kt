package nextstep.subway.path.domain

import nextstep.subway.line.domain.LineStation
import nextstep.subway.station.domain.Station
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class PathStationsTest {
    @Test
    fun addStation() {
        val station = Station("주한역")
        val lineStation = LineStation(1, null, 10, 10)
        val pathStation = PathStation(station, lineStation, null)
        val pathStations = PathStations()

        pathStations.add(pathStation)

        assertThat(pathStations.pathStations).isNotEmpty
        assertThat(pathStations.pathStations[0]::class.java).isEqualTo(PathStation::class.java)
    }
    
    @Test
    fun sameBeforeStationId() {
        val station = Station("주한역")
        val station2 = Station("테스트역")
        val lineStation = LineStation(1, null, 10, 10)
        val pathStations = PathStations()
        pathStations.add(PathStation(station, lineStation, null))
        pathStations.add(PathStation(station, lineStation, 1))
        pathStations.add(PathStation(station, lineStation, 2))
        assertThat(pathStations.pathStations).hasSize(3)

        pathStations.add(PathStation(station2, lineStation, 1))

        assertThat(pathStations.pathStations).hasSize(2)
        assertThat(pathStations.pathStations.last().station).isEqualTo(station2)
        assertThat(pathStations.pathStations.first().beforeStationId).isNull()
    }

    @Test
    fun `리스트의 take 명령어에 음수값이 들어가면 어캐되는지 테스트`() {
        val list = mutableListOf(1,2,3,4)

        val index = list.indexOf(list.find { it == 5 })

        assertThat(index).isEqualTo(-1)
        assertThatThrownBy {
            list.take(index)
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

}
