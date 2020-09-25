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
        val lineStationList = LineStationList()
        if (lineStations.isNotEmpty()) {
            arrangeLineStations(lineStationList)
        }
        return lineStationList.map { LineStationResponse.of(it, stationRepository) }
    }

    private fun arrangeLineStations(list: LineStationList) {
        list.add(lineStations.find { it.preStationId == null } ?: throw RuntimeException())
        while (list.size() < lineStations.size) {
            list.add(lineStations.find { list.last().stationId == it.preStationId } ?: throw RuntimeException())
        }
    }

    fun add(lineStation: LineStation) {
        checkContains(lineStation)
        if (lineStations.isNotEmpty()) {
            val index = lineStations.indexOf(lineStations.find { it.stationId == lineStation.preStationId }
                    ?: lineStations.last())
            lineStation.updatePreStationTo(lineStations[index].stationId)
            lineStations.stream().filter { it.preStationId == lineStation.preStationId }.findFirst().ifPresent { it.updatePreStationTo(lineStation.stationId) }
        }
        lineStations.add(lineStation)
    }

    private fun checkContains(lineStation: LineStation) {
        if (lineStations.any { it.isSame(lineStation) }) {
            throw RuntimeException()
        }
    }
}
