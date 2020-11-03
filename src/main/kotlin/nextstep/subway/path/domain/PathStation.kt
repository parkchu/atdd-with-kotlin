package nextstep.subway.path.domain

import nextstep.subway.line.domain.LineStation
import nextstep.subway.station.domain.Station

class PathStation(val station: Station, lineStation: LineStation, val beforeStationId: Long?) {
    val distance = lineStation.distance
    val duration = lineStation.duration
}
