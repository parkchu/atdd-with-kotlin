package nextstep.subway.line.domain

import nextstep.subway.line.dto.LineStationResponse
import nextstep.subway.station.domain.StationRepository
import nextstep.subway.station.dto.StationResponse
import java.util.*
import javax.persistence.*

@Embeddable
class LineStations {
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "line_id", foreignKey = ForeignKey(name = "fk_line_station_to_line"))
    private val lineStations: MutableList<LineStation> = ArrayList()

    fun getLineStationResponses(stationRepository: StationRepository): List<LineStationResponse> {
        return lineStations.map {
            LineStationResponse(
                    StationResponse.of(stationRepository.findById(it.stationId).orElseThrow { RuntimeException() }),
                    it.preStationId,
                    it.distance,
                    it.duration
            )
        }
    }

    fun add(lineStation: LineStation) {
        lineStations.add(lineStation)
    }
}
