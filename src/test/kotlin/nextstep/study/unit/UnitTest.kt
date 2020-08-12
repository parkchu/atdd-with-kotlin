package nextstep.study.unit

import nextstep.subway.line.domain.Line
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalTime

@DisplayName("단위 테스트")
class UnitTest {
    @DisplayName("단위 테스트 - 단독")
    @Test
    fun update() {
        // given
        val line = Line("신분당선", "RED", LocalTime.now(), LocalTime.now(), 10)
        val newName = "새이름"
        val newLine = Line(newName, "RED", LocalTime.now(), LocalTime.now(), 10)

        // when
        line.update(newLine)

        // then
        assertThat(line.name).isEqualTo(newName)
    }
}

