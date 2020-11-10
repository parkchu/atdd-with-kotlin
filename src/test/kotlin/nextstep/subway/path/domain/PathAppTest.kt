package nextstep.subway.path.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class PathAppTest {
    @Test
    fun setPoint() {
        val pathApp = PathApp()

        pathApp.setPoint("1")
        pathApp.setPoint("2")
        pathApp.setPoint("3")
        val paths = pathApp.getPaths()

        val inf = PathApp.INF
        assertThat((paths.getValue("1")["1"] ?: error("")).distance).isEqualTo(0)
        assertThat((paths.getValue("1")["2"] ?: error("")).distance).isEqualTo(inf)
        assertThat((paths.getValue("1")["3"] ?: error("")).distance).isEqualTo(inf)
        assertThat((paths.getValue("1")["4"])).isNull()
        assertThat((paths.getValue("2")["2"] ?: error("")).distance).isEqualTo(0)
        assertThat((paths.getValue("2")["1"] ?: error("")).distance).isEqualTo(inf)
        assertThat((paths.getValue("2")["3"] ?: error("")).distance).isEqualTo(inf)
        assertThat((paths.getValue("3")["3"] ?: error("")).distance).isEqualTo(0)
        assertThat((paths.getValue("3")["1"] ?: error("")).distance).isEqualTo(inf)
        assertThat((paths.getValue("3")["2"] ?: error("")).distance).isEqualTo(inf)
    }

    @Test
    fun setContainsPoint() {
        val pathApp = PathApp()
        val pointName = "1"
        pathApp.setPoint(pointName)

        assertThatThrownBy {
            pathApp.setPoint(pointName)
        }.isInstanceOf(IllegalArgumentException::class.java).hasMessageContaining("$pointName 은 존재하는 포인트입니다.")
    }

    @Test
    fun setBetweenValue() {
        val pathApp = PathApp()
        val pathName1 = "1"
        val pathName2 = "2"
        val pathName3 = "3"
        pathApp.setPoint(pathName1)
        pathApp.setPoint(pathName2)
        pathApp.setPoint(pathName3)

        pathApp.setBetweenValue(pathName1, pathName2, 10, 10)
        pathApp.setBetweenValue(pathName2, pathName3, 5, 5)

        val paths = pathApp.getPaths()
        val inf = PathApp.INF
        assertThat((paths.getValue("1")["1"] ?: error("")).distance).isEqualTo(0)
        assertThat((paths.getValue("1")["2"] ?: error("")).distance).isEqualTo(10)
        assertThat((paths.getValue("1")["3"] ?: error("")).distance).isEqualTo(inf)
        assertThat((paths.getValue("1")["4"])).isNull()
        assertThat((paths.getValue("2")["2"] ?: error("")).distance).isEqualTo(0)
        assertThat((paths.getValue("2")["1"] ?: error("")).distance).isEqualTo(10)
        assertThat((paths.getValue("2")["3"] ?: error("")).distance).isEqualTo(5)
        assertThat((paths.getValue("3")["3"] ?: error("")).distance).isEqualTo(0)
        assertThat((paths.getValue("3")["1"] ?: error("")).distance).isEqualTo(inf)
        assertThat((paths.getValue("3")["2"] ?: error("")).distance).isEqualTo(5)
    }

    @Test
    fun setSamePoint() {
        val pathApp = PathApp()
        val pathName1 = "1"
        pathApp.setPoint(pathName1)

        assertThatThrownBy {
            pathApp.setBetweenValue(pathName1, pathName1, 10, 10)
        }.isInstanceOf(IllegalArgumentException::class.java).hasMessageContaining("$pathName1 과 $pathName1 은 같은 포인트입니다.")
    }

    @Test
    fun findShortPath() {
        val pathApp = PathApp()
        val pathName1 = "1"
        val pathName2 = "2"
        val pathName3 = "3"
        pathApp.setPoint(pathName1)
        pathApp.setPoint(pathName2)
        pathApp.setPoint(pathName3)

        pathApp.setBetweenValue(pathName1, pathName2, 10, 10)
        pathApp.setBetweenValue(pathName2, pathName3, 5, 5)

        assertThat((pathApp.getShortestPath(pathName1, pathName3)["총"] ?: error("")).first()).isEqualTo("15")
    }
}
