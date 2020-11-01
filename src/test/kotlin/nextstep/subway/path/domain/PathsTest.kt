package nextstep.subway.path.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PathsTest {
    @Test
    fun addPath() {
        val path = Path(listOf())
        val paths = Paths()

        paths.add(path)

        assertThat(paths.paths).hasSize(1)
    }
}
