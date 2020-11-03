package nextstep.subway.path.domain

import nextstep.subway.line.domain.LineStation

class PathStation(lineStation: LineStation, val beforeStationId: Long) {
    val stationId = lineStation.stationId
    val distance = lineStation.distance
    val duration = lineStation.duration
}
