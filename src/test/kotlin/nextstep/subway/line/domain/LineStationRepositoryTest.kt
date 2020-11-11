package nextstep.subway.line.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@DataJpaTest
@EnableJpaAuditing
class LineStationRepositoryTest {
    @Autowired
    private lateinit var lineStationRepository: LineStationRepository

    @Test
    fun findAll() {
        val lineStation = LineStation(1, 1, 1, 1)
        val lineStation2 = LineStation(2, 2, 2, 2)
        lineStationRepository.save(lineStation)
        lineStationRepository.save(lineStation2)

        val lineStations = lineStationRepository.findAll()

        assertThat(lineStations.first().id).isEqualTo(lineStation.id)
        assertThat(lineStations.last().id).isEqualTo(lineStation2.id)
    }
}
