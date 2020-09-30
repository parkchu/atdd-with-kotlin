package nextstep.subway.line.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LineStationsTest {
    @Test
    fun add_line_station() {
        // Give
        val lineStation = LineStation(1, null, 0, 0)

        // When
        val lineStations = LineStations()
        lineStations.add(lineStation)

        // Then
        assertThat(lineStations.lineStations).hasSize(1)
    }
}
