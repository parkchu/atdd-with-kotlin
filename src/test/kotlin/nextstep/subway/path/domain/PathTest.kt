package nextstep.subway.path.domain

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
}
