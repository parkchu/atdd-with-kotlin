package nextstep.subway.path.domain

import nextstep.subway.line.domain.LineStation
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

    @Test
    fun getShortestPath() {
        val paths = Paths()
        val path = Path(
                listOf(
                LineStation(1, null, 10, 10),
                LineStation(2, 1, 10, 10),
                LineStation(3, 2, 10, 10)
                )
        )
        val path2 = Path(
                listOf(
                        LineStation(1, null, 5, 10),
                        LineStation(2, 1, 10, 10),
                        LineStation(3, 2, 10, 10)
                )
        )
        paths.add(path)
        paths.add(path2)

        val shortestPath = paths.getShortestPath()

        assertThat(shortestPath).isEqualTo(path2)
    }

    @Test
    fun getShortestPathByNull() {
        val paths = Paths()

        val shortestPath = paths.getShortestPath()

        assertThat(shortestPath.stations).isEmpty()
        assertThat(shortestPath.distance).isEqualTo(0)
        assertThat(shortestPath.duration).isEqualTo(0)
    }
}
