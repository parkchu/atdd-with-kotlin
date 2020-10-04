package nextstep.subway.line.domain

import nextstep.subway.line.dto.LineStationResponse
import nextstep.subway.station.domain.Station
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.lang.RuntimeException

class LineStationsTest {
    @Test
    fun add_line_station() {
        // Given
        val lineStation = LineStation(1, null, 0, 0)

        // When
        val lineStations = LineStations()
        lineStations.add(lineStation)

        // Then
        assertThat(lineStations.lineStations).hasSize(1)
    }

    @Test
    fun can_not_add_same_station() {
        // Given
        val lineStation = LineStation(1, null, 0, 0)
        val lineStations = LineStations()
        lineStations.add(lineStation)

        // Then
        assertThatThrownBy {
            // When
            lineStations.add(lineStation)
        }.isInstanceOf(RuntimeException::class.java)
    }

    @Test
    fun `LineStation Change LineStationResponse`() {
        // Given
        val stations = listOf(Station("테스트역"))
        val lineStation = LineStation(0, null, 0, 0)
        val lineStations = LineStations()
        lineStations.add(lineStation)

        // When
        val lineStationResponses = lineStations.getLineStationResponses(stations)

        // Then
        assertThat(lineStationResponses[0]::class.java).isEqualTo(LineStationResponse::class.java)
        assertThat(lineStationResponses[0].station.name).isEqualTo("테스트역")
    }

    @Test
    fun `Arrange LineStations`() {
        // Given
        val stations = listOf(Station("테스트역1", 1), Station("테스트역2", 2), Station("테스트역3", 3))
        val lineStation1 = LineStation(stations[0].id, null, 0, 0)
        val lineStation2 = LineStation(stations[1].id, null, 0, 0)
        val lineStation3 = LineStation(stations[2].id, stations[0].id, 0, 0)
        val lineStations = LineStations()
        lineStations.add(lineStation1)
        lineStations.add(lineStation2)
        lineStations.add(lineStation3)

        // When
        val lineStationResponses = lineStations.getLineStationResponses(stations)

        // Then
        assertThat(lineStationResponses.first().station.name).isEqualTo("테스트역1")
        assertThat(lineStationResponses[1].station.name).isEqualTo("테스트역3")
        assertThat(lineStationResponses.last().station.name).isEqualTo("테스트역2")
        assertThat(lineStationResponses.last().preStationId).isEqualTo(lineStation3.stationId)
    }
}
