package nextstep.subway.path.domain

import nextstep.subway.line.domain.LineStation
import nextstep.subway.path.dto.PathStationResponse
import nextstep.subway.station.domain.Station
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PathTest {
    @Test
    fun makePath() {
        val lineStations = listOf(LineStation(1, null, 5, 8), LineStation(2, 1, 7, 1))
        val path = Path(lineStations)

        assertThat(path.stations).hasSize(lineStations.size)
        assertThat(path.distance).isEqualTo(12)
        assertThat(path.duration).isEqualTo(9)
    }

    @Test
    fun getStationResponse() {
        val path = Path(listOf(LineStation(1, null, 10, 10)))
        val stations = listOf(Station("테스트역", id = 1))

        val pathStationResponse = path.getStationsResponse(stations)

        assertThat(pathStationResponse.first().id).isEqualTo(stations.first().id)
        assertThat(pathStationResponse.first().name).isEqualTo(stations.first().name)
        assertThat(pathStationResponse.first().createdAt).isEqualTo(stations.first().createdDate)
        assertThat(pathStationResponse.first()::class.java).isEqualTo(PathStationResponse::class.java)
    }
}
