package nextstep.subway.path.domain

import nextstep.subway.line.domain.LineStation
import nextstep.subway.line.domain.LineStationRepository
import nextstep.subway.path.application.PathService
import nextstep.subway.station.domain.Station
import nextstep.subway.station.domain.StationRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class PathServiceTest {
    @Mock
    private lateinit var lineStationRepository: LineStationRepository

    @Mock
    private lateinit var stationRepository: StationRepository

    @DisplayName("출발역과 도착역이 붙어있을경우 경로 구하기")
    @Test
    fun findPath() {
        val lineStation1 = LineStation(1, null, 5, 10)
        val lineStation2 = LineStation(2, 1, 10, 10)
        val lineStations = listOf(lineStation1, lineStation2)
        `when`(lineStationRepository.findAll()).thenReturn(lineStations)
        val station1 = Station("출발역", id = 1)
        val station2 = Station("도착역", id = 2)
        val stations = listOf(station1, station2)
        `when`(stationRepository.findAll()).thenReturn(stations)
        val pathService = PathService(lineStationRepository, stationRepository)

        val path = pathService.findShortest(station1.id, station2.id)

        assertThat(path.stations.first().id).isEqualTo(stations.first().id)
        assertThat(path.stations.last().id).isEqualTo(stations.last().id)
        assertThat(path.distance).isEqualTo(15)
        assertThat(path.duration).isEqualTo(20)
    }
}
