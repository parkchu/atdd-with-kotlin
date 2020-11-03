package nextstep.subway.path.domain

import nextstep.subway.line.domain.LineStation
import nextstep.subway.station.domain.Station

class PathStation(val station: Station, lineStation: LineStation = LineStation(0, null, 0, 0), beforeStationId: Long?) {
    val distance = lineStation.distance
    val duration = lineStation.duration
}
