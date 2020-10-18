package nextstep.subway.line.domain

import org.mockito.Mockito.`when`
import nextstep.subway.line.application.LineService
import nextstep.subway.line.dto.LineStationRequest
import nextstep.subway.station.domain.Station
import nextstep.subway.station.domain.StationRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class LineStationServiceTest {
    @Mock
    private lateinit var lineRepository: LineRepository

    @Mock
    private lateinit var stationRepository: StationRepository

    @Test
    fun addLineStation() {
        // Given
        val line = Line("테스트선", "yellow",  LocalTime.of(5, 30), LocalTime.of(23, 30), 5)
        val station = Station("테스트역")
        `when`(lineRepository.findById(line.id)).thenReturn(Optional.of(line))
        `when`(stationRepository.findById(station.id)).thenReturn(Optional.of(station))
        `when`(stationRepository.findAll()).thenReturn(listOf(station))
        val lineService = LineService(lineRepository, stationRepository)

        // When
        val lineStationRequest = LineStationRequest(station.id, null, 1, 1)
        lineService.addStation(line.id, lineStationRequest)

        // Then
        val lineStations = lineService.findLineById(line.id).stations
        assertThat(lineStations).hasSize(1)

    }
}
