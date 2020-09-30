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
        val lineStationResponse = lineStations.getLineStationResponses(stations)

        // Then
        assertThat(lineStationResponse[0]::class.java).isEqualTo(LineStationResponse::class.java)
        assertThat(lineStationResponse[0].station.name).isEqualTo("테스트역")
    }
}
