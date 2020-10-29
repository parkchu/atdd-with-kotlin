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
        val lineStation = LineStation(1, 1, 10, 10)

        path.add(lineStation)

        assertThat(path.stations.first()).isEqualTo(lineStation.stationId)
        assertThat(path.distance).isEqualTo(10)
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
}
