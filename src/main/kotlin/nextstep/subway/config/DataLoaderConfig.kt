package nextstep.subway.config

import com.google.common.collect.Lists
import nextstep.subway.line.domain.Line
import nextstep.subway.line.domain.LineRepository
import nextstep.subway.line.domain.LineStation
import nextstep.subway.station.domain.Station
import nextstep.subway.station.domain.StationRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.time.LocalTime

@Component
class DataLoaderConfig(private val stationRepository: StationRepository, private val lineRepository: LineRepository) : CommandLineRunner {
    @Throws(Exception::class)
    override fun run(vararg args: String) {
        val station1 = Station("강남역")
        val station2 = Station("교대역")
        val station3 = Station("양재역")
        val station4 = Station("남부터미널역")
        stationRepository.saveAll(Lists.newArrayList(station1, station2, station3, station4))
        val line1 = Line("신분당선", "red lighten-1", LocalTime.now(), LocalTime.now(), 10)
        val line2 = Line("2호선", "green lighten-1", LocalTime.now(), LocalTime.now(), 10)
        val line3 = Line("3호선", "orange darken-1", LocalTime.now(), LocalTime.now(), 10)
        line1.addStation(LineStation(1L, null, 0, 0))
        line1.addStation(LineStation(3L, 1L, 3, 1))
        line2.addStation(LineStation(2L, null, 0, 0))
        line2.addStation(LineStation(1L, 2L, 3, 1))
        line3.addStation(LineStation(2L, null, 0, 0))
        line3.addStation(LineStation(4L, 2L, 2, 10))
        line3.addStation(LineStation(3L, 4L, 2, 10))
        lineRepository.saveAll(Lists.newArrayList(line1, line2, line3))
    }
}
