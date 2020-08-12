package nextstep.study.unit

import nextstep.subway.line.application.LineService
import nextstep.subway.line.domain.Line
import nextstep.subway.line.domain.LineRepository
import nextstep.subway.station.domain.StationRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalTime

@DisplayName("단위 테스트 - mockito의 MockitoExtension을 활용한 가짜 협력 객체 사용")
@ExtendWith(MockitoExtension::class)
class MockitoExtensionTest {
    @Mock
    private lateinit var lineRepository: LineRepository

    @Mock
    private lateinit var stationRepository: StationRepository

    @Test
    fun findAllLines() {
        // given
        val line = Line("신분당선", "bg-red-600",
                LocalTime.of(5, 30), LocalTime.of(23, 30), 5)
        `when`(lineRepository.findAll()).thenReturn(listOf(line))
        val lineService = LineService(lineRepository, stationRepository)

        // when
        val responses = lineService.findAllLines()

        // then
        assertThat(responses).hasSize(1)
    }
}
