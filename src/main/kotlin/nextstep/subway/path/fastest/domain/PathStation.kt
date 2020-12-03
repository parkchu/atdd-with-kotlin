package nextstep.subway.path.fastest.domain

import nextstep.subway.line.domain.LineStation
import nextstep.subway.station.domain.Station
import java.time.LocalTime

class PathStation(val station: Station, private val lineStation: LineStation, val beforeStationId: Long?) {
    fun getDuration(time: LocalTime): Int {
        return lineStation.getDuration(time)
    }

    fun getDistance(): Int {
        return lineStation.distance
    }

    fun getFare(): Int {
        return lineStation.extraFare
    }
}
