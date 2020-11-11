package nextstep.subway.path.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PathAppTest {

    @Test
    fun findShortPath() {
        val paths = Paths()
        val pathName1 = "1"
        val pathName2 = "2"
        val pathName3 = "3"
        paths.setPoint(pathName1)
        paths.setPoint(pathName2)
        paths.setPoint(pathName3)

        paths.setBetweenValue(pathName1, pathName2, 10, 10)
        paths.setBetweenValue(pathName2, pathName3, 5, 5)

        val pathApp = PathApp()
        assertThat((pathApp.getShortestPath(paths, pathName1, pathName3)["총"] ?: error("")).first()).isEqualTo("15")
    }
}
