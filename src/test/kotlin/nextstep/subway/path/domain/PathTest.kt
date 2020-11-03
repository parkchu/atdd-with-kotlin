package nextstep.subway.path.domain

import nextstep.subway.line.domain.LineStation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PathTest {
    @Test
    fun makePath() {
        val stations = listOf(LineStation(1, null, 10, 10), LineStation(2, 1, 10, 10))
        val path = Path(stations.map { PathStation(it, 1) })

        assertThat(path.stations).isNotEmpty
        assertThat(path.distance).isEqualTo(20)
        assertThat(path.duration).isEqualTo(20)
    }
}
