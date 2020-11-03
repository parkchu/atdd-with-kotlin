package nextstep.subway.path.domain

import nextstep.subway.line.domain.LineStation
import nextstep.subway.path.dto.PathStationResponse
import nextstep.subway.station.domain.Station
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PathTest {
    private val stations = listOf(Station("주한역"), Station("테스트역"))
    private val lineStations = listOf(LineStation(1, null, 10, 10), LineStation(2, 1, 10, 10))
    private val pathStations = listOf(PathStation(stations[0], lineStations[0], null), PathStation(stations[1], lineStations[1], 1))
    private val path = Path(pathStations)

    @Test
    fun checkPath() {
        assertThat(path.stations).isNotEmpty
        assertThat(path.stations[0].station).isEqualTo(stations[0])
        assertThat(path.distance).isEqualTo(20)
        assertThat(path.duration).isEqualTo(20)
    }

    @Test
    fun getStationsResponse() {
        val responses = path.getStationsResponse()

        assertThat(responses[0]::class.java).isEqualTo(PathStationResponse::class.java)
        assertThat(responses[0].name).isEqualTo(stations[0].name)
        assertThat(responses[1].name).isEqualTo(stations[1].name)
    }
}
