package nextstep.subway.line.domain

import nextstep.subway.station.domain.Station
import nextstep.subway.station.domain.StationRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.time.LocalTime

@DataJpaTest
@EnableJpaAuditing
class LineRepositoryTest {
    @Autowired
    private lateinit var lineRepository: LineRepository

    @Autowired
    private lateinit var stationsRepository: StationRepository

    @Test
    fun `역 추가`() {
        val station1 = stationsRepository.save(Station("피카추역"))
        val station2 = stationsRepository.save(Station("라이추역"))

        val startTime = LocalTime.of(5, 0, 0)
        val endTime = LocalTime.of(12, 0, 0)
        val line = Line("포캣몬선", "노란색", startTime, endTime, 5)
        line.addStation(LineStation(station1.id, null, 5, 3))
        line.addStation(LineStation(station2.id, station1.id, 7, 5))

        lineRepository.save(line)
    }
}
