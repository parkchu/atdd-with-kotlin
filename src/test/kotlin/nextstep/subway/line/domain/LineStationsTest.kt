package nextstep.subway.line.domain

import nextstep.subway.line.dto.LineStationResponse
import nextstep.subway.station.domain.Station
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.RuntimeException

class LineStationsTest {
    private val lineStations = LineStations()
    private val stations = listOf(Station("테스트역1", 1), Station("테스트역2", 2), Station("테스트역3", 3))
    private val lineStation1 = LineStation(stations[0].id, null, 0, 0)
    private val lineStation2 = LineStation(stations[1].id, null, 0, 0)
    private val lineStation3 = LineStation(stations[2].id, stations[0].id, 0, 0)

    @BeforeEach
    fun backGround() {
        lineStations.add(lineStation1)
        lineStations.add(lineStation2)
        lineStations.add(lineStation3)
    }

    @Test
    fun add_line_station() {
        // Given
        val lineStation = LineStation(1000, null, 0, 0)

        // When
        val currentLineStationsSize = lineStations.lineStations.size
        lineStations.add(lineStation)

        // Then
        assertThat(lineStations.lineStations).hasSize(currentLineStationsSize + 1)
    }

    @Test
    fun can_not_add_same_station() {
        // Then
        assertThatThrownBy {
            // When
            lineStations.add(lineStation1)
        }.isInstanceOf(RuntimeException::class.java)
    }

    @Test
    fun `LineStation Change LineStationResponse`() {
        // When
        val lineStationResponses = lineStations.getLineStationResponses(stations)

        // Then
        assertThat(lineStationResponses[0]::class.java).isEqualTo(LineStationResponse::class.java)
        assertThat(lineStationResponses[0].station.name).isEqualTo(stations[0].name)
    }

    @Test
    fun `Arrange LineStations`() {
        // When
        val lineStationResponses = lineStations.getLineStationResponses(stations)

        // Then
        assertThat(lineStationResponses.first().station.name).isEqualTo(stations[0].name)
        assertThat(lineStationResponses[1].station.name).isEqualTo(stations[2].name)
        assertThat(lineStationResponses.last().station.name).isEqualTo(stations[1].name)
        assertThat(lineStationResponses.last().preStationId).isEqualTo(lineStation3.stationId)
    }

    @Test
    fun `Delete LineStation`() {
        // When
        val currentSize = lineStations.lineStations.size
        lineStations.delete(stations[0].id)

        // Then
        assertThat(lineStations.lineStations).hasSize(currentSize - 1)
        assertThat(lineStations.lineStations).doesNotContain(lineStation1)
    }
}
