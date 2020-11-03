package nextstep.subway.path.domain

import nextstep.subway.line.domain.LineStation
import nextstep.subway.station.domain.Station
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PathStationsTest {
    @Test
    fun addStation() {
        val station = Station("주한역")
        val lineStation = LineStation(1, null, 10, 10)
        val pathStations = PathStations()

        pathStations.add(station, lineStation, null)

        assertThat(pathStations.stations).isNotEmpty
        assertThat(pathStations.stations[0]::class.java).isEqualTo(PathStation::class.java)
    }
}
