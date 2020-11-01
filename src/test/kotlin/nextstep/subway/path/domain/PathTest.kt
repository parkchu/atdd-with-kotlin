package nextstep.subway.path.domain

import nextstep.subway.line.domain.LineStation
import nextstep.subway.path.dto.PathStationResponse
import nextstep.subway.station.domain.Station
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PathTest {
    @Test
    fun makePath() {
        val path = Path(1, 2)

        assertThat(path.startStationId).isEqualTo(1)
        assertThat(path.arrivalStationId).isEqualTo(2)
        assertThat(path.distance).isEqualTo(0)
        assertThat(path.duration).isEqualTo(0)
        assertThat(path.stations).isEmpty()
    }
    
    @Test
    fun addStation() {
        val path = Path(1, 1)
        val lineStation = LineStation(1, 1, 5, 10)

        path.add(lineStation)

        assertThat(path.stations.first()).isEqualTo(lineStation)
        assertThat(path.distance).isEqualTo(5)
        assertThat(path.duration).isEqualTo(10)
    }

    @Test
    fun getStationResponse() {
        val path = Path(1, 1)
        val stations = listOf(Station("테스트역", id = 1))
        val lineStation = LineStation(1, null, 10, 10)
        path.add(lineStation)

        val pathStationResponse = path.getStationsResponse(stations)

        assertThat(pathStationResponse.first().id).isEqualTo(stations.first().id)
        assertThat(pathStationResponse.first().name).isEqualTo(stations.first().name)
        assertThat(pathStationResponse.first().createdAt).isEqualTo(stations.first().createdDate)
        assertThat(pathStationResponse.first()::class.java).isEqualTo(PathStationResponse::class.java)
    }

    @Test
    fun addSameLineStation() {
        val path = Path(1, 1)
        val lineStation = LineStation(1, null, 5, 10)
        val lineStation2 = LineStation(2, 1, 5, 10)
        val lineStation3 = LineStation(3, 2, 5, 10)
        path.add(lineStation)
        path.add(lineStation2)
        path.add(lineStation3)
        assertThat(path.stations.last()).isEqualTo(lineStation3)
        assertThat(path.stations).hasSize(3)

        path.add(lineStation2)

        assertThat(path.stations.last()).isEqualTo(lineStation2)
        assertThat(path.stations).hasSize(2)

    }
}
