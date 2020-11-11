package nextstep.subway.path.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class PathsTest {
    @Test
    fun setPoint() {
        val paths = Paths()

        paths.setPoint("1")
        paths.setPoint("2")
        paths.setPoint("3")
        val pathsMap = paths.getPaths()

        val inf = Paths.INF
        assertThat((pathsMap.getValue("1")["1"] ?: error("")).distance).isEqualTo(0)
        assertThat((pathsMap.getValue("1")["2"] ?: error("")).distance).isEqualTo(inf)
        assertThat((pathsMap.getValue("1")["3"] ?: error("")).distance).isEqualTo(inf)
        assertThat((pathsMap.getValue("1")["4"])).isNull()
        assertThat((pathsMap.getValue("2")["2"] ?: error("")).distance).isEqualTo(0)
        assertThat((pathsMap.getValue("2")["1"] ?: error("")).distance).isEqualTo(inf)
        assertThat((pathsMap.getValue("2")["3"] ?: error("")).distance).isEqualTo(inf)
        assertThat((pathsMap.getValue("3")["3"] ?: error("")).distance).isEqualTo(0)
        assertThat((pathsMap.getValue("3")["1"] ?: error("")).distance).isEqualTo(inf)
        assertThat((pathsMap.getValue("3")["2"] ?: error("")).distance).isEqualTo(inf)
    }

    @Test
    fun setContainsPoint() {
        val paths = Paths()
        val pointName = "1"
        paths.setPoint(pointName)

        assertThatThrownBy {
            paths.setPoint(pointName)
        }.isInstanceOf(IllegalArgumentException::class.java).hasMessageContaining("$pointName 은 존재하는 포인트입니다.")
    }

    @Test
    fun setBetweenValue() {
        val paths = Paths()
        val pathName1 = "1"
        val pathName2 = "2"
        val pathName3 = "3"
        paths.setPoint(pathName1)
        paths.setPoint(pathName2)
        paths.setPoint(pathName3)

        paths.setBetweenValue(pathName1, pathName2, listOf(10, 10))
        paths.setBetweenValue(pathName2, pathName3, listOf(5, 5))

        val pathsMap = paths.getPaths()
        val inf = Paths.INF
        assertThat((pathsMap.getValue("1")["1"] ?: error("")).distance).isEqualTo(0)
        assertThat((pathsMap.getValue("1")["2"] ?: error("")).distance).isEqualTo(10)
        assertThat((pathsMap.getValue("1")["3"] ?: error("")).distance).isEqualTo(inf)
        assertThat((pathsMap.getValue("1")["4"])).isNull()
        assertThat((pathsMap.getValue("2")["2"] ?: error("")).distance).isEqualTo(0)
        assertThat((pathsMap.getValue("2")["1"] ?: error("")).distance).isEqualTo(10)
        assertThat((pathsMap.getValue("2")["3"] ?: error("")).distance).isEqualTo(5)
        assertThat((pathsMap.getValue("3")["3"] ?: error("")).distance).isEqualTo(0)
        assertThat((pathsMap.getValue("3")["1"] ?: error("")).distance).isEqualTo(inf)
        assertThat((pathsMap.getValue("3")["2"] ?: error("")).distance).isEqualTo(5)
    }

    @Test
    fun setSamePoint() {
        val paths = Paths()
        val pathName1 = "1"
        paths.setPoint(pathName1)

        assertThatThrownBy {
            paths.setBetweenValue(pathName1, pathName1, listOf(10, 10))
        }.isInstanceOf(IllegalArgumentException::class.java).hasMessageContaining("$pathName1 과 $pathName1 은 같은 포인트입니다.")
    }

    @Test
    fun getMinPoint() {
        val paths = Paths()
        val pathName1 = "1"
        val pathName2 = "2"
        val pathName3 = "3"
        paths.setPoint(pathName1)
        paths.setPoint(pathName2)
        paths.setPoint(pathName3)
        paths.setBetweenValue(pathName1, pathName2, listOf(10, 10))
        paths.setBetweenValue(pathName2, pathName3, listOf(5, 5))

        paths.addVisitPoint(pathName1)
        val result = paths.getMinPoint(pathName1, paths.getPathsItToMap(pathName1))

        assertThat(result).isEqualTo(pathName2)
    }
}
