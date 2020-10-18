package nextstep.subway.line.domain

import org.mockito.Mockito.`when`
import nextstep.subway.line.application.LineService
import nextstep.subway.line.dto.LineStationRequest
import nextstep.subway.station.domain.Station
import nextstep.subway.station.domain.StationRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
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

    private val line = Line("테스트선", "yellow",  LocalTime.of(5, 30), LocalTime.of(23, 30), 5)
    private val station = Station("테스트역")
    private val lineStationRequest = LineStationRequest(station.id, null, 1, 1)

    @BeforeEach
    fun backGround() {
        `when`(lineRepository.findById(line.id)).thenReturn(Optional.of(line))
        `when`(stationRepository.findById(station.id)).thenReturn(Optional.of(station))
        `when`(stationRepository.findAll()).thenReturn(listOf(station))
    }

    @Test
    fun addLineStation() {
        // Given
        val lineService = LineService(lineRepository, stationRepository)

        // When
        lineService.addStation(line.id, lineStationRequest)

        // Then
        val lineStations = lineService.findLineById(line.id).stations
        assertThat(lineStations).hasSize(1)

    }

    @Test
    fun deleteLineStation() {
        // Given
        val lineService = LineService(lineRepository, stationRepository)
        lineService.addStation(line.id, lineStationRequest)

        // When
        lineService.deleteStation(line.id, station.id)

        // Then
        val lineStations = lineService.findLineById(line.id).stations
        assertThat(lineStations).isEmpty()
    }

    @Test
    fun deleteLineStationAllLine() {
        // Given
        val lineService = LineService(lineRepository, stationRepository)
        lineService.addStation(line.id, lineStationRequest)
        `when`(lineRepository.findByStationContains(station.id)).thenReturn(listOf(line).filter { line -> line.lineStations.lineStations.any { it.stationId == station.id } })

        // When
        lineService.deleteStationAllLine(station.id)

        // Then
        val lineStations = lineService.findLineById(line.id).stations
        assertThat(lineStations).isEmpty()
    }
}
