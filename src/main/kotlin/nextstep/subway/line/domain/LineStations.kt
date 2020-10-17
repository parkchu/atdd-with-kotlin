package nextstep.subway.line.domain

import nextstep.subway.line.dto.LineStationResponse
import nextstep.subway.station.domain.Station
import org.springframework.dao.DataIntegrityViolationException
import java.util.*
import javax.persistence.*

@Embeddable
class LineStations {
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "line_id", foreignKey = ForeignKey(name = "fk_line_station_to_line"))
    private val _lineStations: MutableList<LineStation> = ArrayList()
    val lineStations: List<LineStation>
        get() = _lineStations.toList()

    fun getLineStationResponses(stations: List<Station>): List<LineStationResponse> {
        val lineStationList = LineStationList()
        if (_lineStations.isNotEmpty()) {
            arrangeLineStations(lineStationList)
        }
        return lineStationList.map { lineStation -> LineStationResponse.of(lineStation, stations) }
    }

    private fun arrangeLineStations(list: LineStationList) {
        list.add(_lineStations.find { it.preStationId == null } ?: throw RuntimeException())
        while (list.size() < _lineStations.size) {
            list.add(_lineStations.find { list.last().stationId == it.preStationId } ?: throw RuntimeException())
        }
    }

    fun add(lineStation: LineStation) {
        checkContains(lineStation)
        if (_lineStations.isNotEmpty()) {
            val index = _lineStations.indexOf(_lineStations.find { it.stationId == lineStation.preStationId }
                    ?: _lineStations.last())
            lineStation.updatePreStationTo(_lineStations[index].stationId)
            _lineStations.stream().filter { it.preStationId == lineStation.preStationId }.findFirst().ifPresent { it.updatePreStationTo(lineStation.stationId) }
        }
        _lineStations.add(lineStation)
    }

    private fun checkContains(lineStation: LineStation) {
        if (_lineStations.any { it.isSame(lineStation) }) {
            throw DataIntegrityViolationException("")
        }
    }

    fun delete(stationId: Long) {
        val deleteLineStation = _lineStations.find { it.stationId == stationId } ?: throw DataIntegrityViolationException("")
        _lineStations.find { it.preStationId == stationId }?.updatePreStationTo(deleteLineStation.preStationId)
        _lineStations.removeIf { it.stationId == stationId }
    }
}
