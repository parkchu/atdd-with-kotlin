package nextstep.subway.path.shortest.domain

import nextstep.subway.line.domain.LineStation
import nextstep.subway.line.domain.LineStationRepository
import nextstep.subway.path.shortest.application.PathService
import nextstep.subway.station.domain.Station
import nextstep.subway.station.domain.StationRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.quality.Strictness
import java.util.*

@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PathServiceTest {
    @Mock
    private lateinit var lineStationRepository: LineStationRepository

    @Mock
    private lateinit var stationRepository: StationRepository

    @DisplayName("경로 구하기, 더 비싼 노선의 추가 요금 구하기")
    @Test
    fun findPath() {
        val lineStation2 = LineStation(2, 1, 10, 10, 100)
        val lineStation3 = LineStation(3, 2, 10, 10, 300)
        val lineStations = listOf(lineStation2, lineStation3)
        `when`(lineStationRepository.findAll()).thenReturn(lineStations)
        val station1 = Station("출발역", id = 1)
        val station2 = Station("중간역", id = 2)
        val station3 = Station("도착역", id = 3)
        val stations = listOf(station1, station2, station3)
        `when`(stationRepository.findAll()).thenReturn(stations)
        `when`(stationRepository.findById(station1.id)).thenReturn(Optional.of(station1))
        `when`(stationRepository.findById(station2.id)).thenReturn(Optional.of(station2))
        `when`(stationRepository.findById(station3.id)).thenReturn(Optional.of(station3))
        `when`(stationRepository.findByName(station1.name)).thenReturn(station1)
        `when`(stationRepository.findByName(station2.name)).thenReturn(station2)
        `when`(stationRepository.findByName(station3.name)).thenReturn(station3)
        val pathService = PathService(lineStationRepository, stationRepository)

        val path = pathService.findShortest(listOf(station1.id, station3.id), "DISTANCE")

        assertThat(path.stations.first().id).isEqualTo(station1.id)
        assertThat(path.stations[1].id).isEqualTo(station2.id)
        assertThat(path.stations.last().id).isEqualTo(station3.id)
        assertThat(path.stations).hasSize(3)
        assertThat(path.distance).isEqualTo(20)
        assertThat(path.duration).isEqualTo(20)
        assertThat(path.fare).isEqualTo(300)
    }

    @DisplayName("2개의 경로중 최단 경로 구하기")
    @Test
    fun findPath2() {
        val lineStation2 = LineStation(2, 1, 10, 10)
        val lineStation3 = LineStation(3, 2, 10, 10)
        val lineStation4 = LineStation(1, 4, 5, 5)
        val lineStation5 = LineStation(4, 3, 5, 8)
        val lineStations = listOf(lineStation2, lineStation3, lineStation4, lineStation5)
        `when`(lineStationRepository.findAll()).thenReturn(lineStations)
        val station1 = Station("출발역", id = 1)
        val station2 = Station("중간역", id = 2)
        val station3 = Station("도착역", id = 3)
        val station4 = Station("주한역", id = 4)
        val stations = listOf(station1, station2, station3, station4)
        `when`(stationRepository.findAll()).thenReturn(stations)
        `when`(stationRepository.findById(station1.id)).thenReturn(Optional.of(station1))
        `when`(stationRepository.findById(station2.id)).thenReturn(Optional.of(station2))
        `when`(stationRepository.findById(station3.id)).thenReturn(Optional.of(station3))
        `when`(stationRepository.findByName(station1.name)).thenReturn(station1)
        `when`(stationRepository.findByName(station2.name)).thenReturn(station2)
        `when`(stationRepository.findByName(station3.name)).thenReturn(station3)
        `when`(stationRepository.findByName(station4.name)).thenReturn(station4)
        val pathService = PathService(lineStationRepository, stationRepository)

        val path = pathService.findShortest(listOf(station1.id, station3.id), "DISTANCE")

        assertThat(path.stations.first().id).isEqualTo(station1.id)
        assertThat(path.stations[1].id).isEqualTo(station4.id)
        assertThat(path.stations.last().id).isEqualTo(station3.id)
        assertThat(path.stations).hasSize(3)
        assertThat(path.distance).isEqualTo(10)
        assertThat(path.duration).isEqualTo(13)
    }

    @DisplayName("출발역이 없을 경우")
    @Test
    fun findPath3() {
        val station2 = Station("중간역", id = 2)
        val station3 = Station("도착역", id = 3)
        val station4 = Station("주한역", id = 4)
        val stations = listOf(station2, station3, station4)
        `when`(stationRepository.findAll()).thenReturn(stations)

        val pathService = PathService(lineStationRepository, stationRepository)

        assertThatThrownBy {
            pathService.findShortest(listOf(1, 3), "DISTANCE")
        }.isInstanceOf(RuntimeException::class.java).hasMessageContaining("해당 역은 존재하지 않는다.")
    }

    @DisplayName("출발역과 도착역을 이어주는 역이 없을 경우")
    @Test
    fun findPath4() {
        val lineStation1 = LineStation(2, 1, 10, 10)
        val lineStation3 = LineStation(4, 3, 10, 10)
        val lineStations = listOf(lineStation3, lineStation1)
        `when`(lineStationRepository.findAll()).thenReturn(lineStations)
        val station1 = Station("출발역", id = 1)
        val station2 = Station("중간역", id = 2)
        val station3 = Station("도착역", id = 4)
        val stations = listOf(station1, station3, station2)
        `when`(stationRepository.findAll()).thenReturn(stations)
        `when`(stationRepository.findById(station1.id)).thenReturn(Optional.of(station1))
        `when`(stationRepository.findById(station2.id)).thenReturn(Optional.of(station2))
        `when`(stationRepository.findById(station3.id)).thenReturn(Optional.of(station3))
        `when`(stationRepository.findByName(station1.name)).thenReturn(station1)
        `when`(stationRepository.findByName(station2.name)).thenReturn(station2)
        `when`(stationRepository.findByName(station3.name)).thenReturn(station3)
        val pathService = PathService(lineStationRepository, stationRepository)

        val path = pathService.findShortest(listOf(1, 4), "DISTANCE")

        assertThat(path.distance).isEqualTo(Paths.INF)
    }

    @DisplayName("출발역과 도착역이 같을 경우")
    @Test
    fun findPath5() {
        val pathService = PathService(lineStationRepository, stationRepository)
        assertThatThrownBy {
            pathService.findShortest(listOf(1, 1), "DISTANCE")
        }.isInstanceOf(RuntimeException::class.java).hasMessageContaining("해당 역은 존재하지 않는다.")
    }

    @DisplayName("도착역이 존재하지 않는 경우")
    @Test
    fun findPath6() {
        val station1 = Station("출발역", id = 1)
        val station2 = Station("중간역", id = 2)
        val stations = listOf(station1, station2)
        `when`(stationRepository.findAll()).thenReturn(stations)
        val pathService = PathService(lineStationRepository, stationRepository)
        assertThatThrownBy {
            pathService.findShortest(listOf(1, 3), "DISTANCE")
        }.isInstanceOf(RuntimeException::class.java).hasMessageContaining("해당 역은 존재하지 않는다.")
    }

    @DisplayName("최단 시간 경로 구하기")
    @Test
    fun findPath7() {
        val lineStation2 = LineStation(2, 1, 10, 10)
        val lineStation3 = LineStation(3, 2, 10, 10)
        val lineStation4 = LineStation(1, 4, 20, 5)
        val lineStation5 = LineStation(4, 3, 10, 8)
        val lineStations = listOf(lineStation2, lineStation3, lineStation4, lineStation5)
        `when`(lineStationRepository.findAll()).thenReturn(lineStations)
        val station1 = Station("출발역", id = 1)
        val station2 = Station("중간역", id = 2)
        val station3 = Station("도착역", id = 3)
        val station4 = Station("주한역", id = 4)
        val stations = listOf(station1, station2, station3, station4)
        `when`(stationRepository.findAll()).thenReturn(stations)
        `when`(stationRepository.findById(station1.id)).thenReturn(Optional.of(station1))
        `when`(stationRepository.findById(station2.id)).thenReturn(Optional.of(station2))
        `when`(stationRepository.findById(station3.id)).thenReturn(Optional.of(station3))
        `when`(stationRepository.findByName(station1.name)).thenReturn(station1)
        `when`(stationRepository.findByName(station2.name)).thenReturn(station2)
        `when`(stationRepository.findByName(station3.name)).thenReturn(station3)
        `when`(stationRepository.findByName(station4.name)).thenReturn(station4)
        val pathService = PathService(lineStationRepository, stationRepository)

        val path = pathService.findShortest(listOf(station1.id, station3.id), "DURATION")

        assertThat(path.stations.first().id).isEqualTo(station1.id)
        assertThat(path.stations[1].id).isEqualTo(station4.id)
        assertThat(path.stations.last().id).isEqualTo(station3.id)
        assertThat(path.stations).hasSize(3)
        assertThat(path.distance).isEqualTo(30)
        assertThat(path.duration).isEqualTo(13)
    }

    @DisplayName("요금 구하기")
    @Test
    fun getTotalPrice() {
        val lineStation2 = LineStation(2, 1, 10, 10)
        val lineStation3 = LineStation(3, 2, 10, 10)
        val lineStation4 = LineStation(1, 4, 20, 5)
        val lineStation5 = LineStation(4, 3, 10, 8)
        val lineStations = listOf(lineStation2, lineStation3, lineStation4, lineStation5)
        `when`(lineStationRepository.findAll()).thenReturn(lineStations)
        val station1 = Station("출발역", id = 1)
        val station2 = Station("중간역", id = 2)
        val station3 = Station("도착역", id = 3)
        val station4 = Station("주한역", id = 4)
        val stations = listOf(station1, station2, station3, station4)
        `when`(stationRepository.findAll()).thenReturn(stations)
        `when`(stationRepository.findById(station1.id)).thenReturn(Optional.of(station1))
        `when`(stationRepository.findById(station2.id)).thenReturn(Optional.of(station2))
        `when`(stationRepository.findById(station3.id)).thenReturn(Optional.of(station3))
        `when`(stationRepository.findByName(station1.name)).thenReturn(station1)
        `when`(stationRepository.findByName(station2.name)).thenReturn(station2)
        `when`(stationRepository.findByName(station3.name)).thenReturn(station3)
        `when`(stationRepository.findByName(station4.name)).thenReturn(station4)
        val pathService = PathService(lineStationRepository, stationRepository)
        val path = pathService.findShortest(listOf(station1.id, station3.id), "DURATION")

        path.updateFareWithAge(20)

        assertThat(path.fare).isEqualTo(1_650)
    }
}
