package nextstep.subway.line.domain

import nextstep.subway.line.dto.LineStationResponse
import nextstep.subway.station.domain.StationRepository
import java.util.*
import javax.persistence.*

@Embeddable
class LineStations {
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "line_id", foreignKey = ForeignKey(name = "fk_line_station_to_line"))
    private val lineStations: MutableList<LineStation> = ArrayList()

    fun getLineStationResponses(stationRepository: StationRepository): List<LineStationResponse> {
        return lineStations.map {
            LineStationResponse.of(it, stationRepository)
        }
    }

    fun add(lineStation: LineStation) {
        checkContains(lineStation)
        lineStations.add(lineStation)
    }

    private fun checkContains(lineStation: LineStation) {
        if (lineStations.any { it.isSame(lineStation) }) {
            throw RuntimeException()
        }
    }
}
