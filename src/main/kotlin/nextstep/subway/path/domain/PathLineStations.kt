package nextstep.subway.path.domain

import nextstep.subway.line.domain.LineStation

class PathLineStations(private val _lineStations: List<LineStation>) {
    fun getMinLineStation(type: String): LineStation? {
        return if (type == "DISTANCE") {
            _lineStations.minBy { it.distance }
        } else {
            _lineStations.minBy { it.duration }
        }
    }
}
