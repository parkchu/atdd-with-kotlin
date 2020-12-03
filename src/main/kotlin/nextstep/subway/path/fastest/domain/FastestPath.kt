package nextstep.subway.path.fastest.domain

import nextstep.subway.path.shortest.dto.PathStationResponse
import java.time.LocalTime

class FastestPath(val stations: List<PathStation>) {
    private val _stations = stations.drop(1)

    fun add(path: FastestPath): FastestPath {
        val lineStations = _stations.toMutableList()
        lineStations.add(path._stations.last())
        return FastestPath(lineStations)
    }

    fun getStationsResponse(): List<PathStationResponse> {
        return stations.map { PathStationResponse.of(it.station) }
    }

    fun getDistance(): Int {
        return _stations.sumBy { it.getDistance() }
    }

    fun getDuration(time: String): Int {
        var currentTime = changeLocalTime(time)
        var total = 0
        _stations.forEach {
            val duration = it.getDuration(currentTime)
            total += duration
            currentTime = currentTime.plusMinutes(duration.toLong())
        }
        return total
    }

    private fun changeLocalTime(time: String): LocalTime {
        val length = time.length
        val hourRange = length - 4 until length - 2
        val minuteRange = length - 2 until length
        val hour = time.slice(hourRange).toInt()
        val minute = time.slice(minuteRange).toInt()
        return LocalTime.of(hour, minute)
    }

    fun getFare(): Int {
        val station = _stations.maxBy { it.getFare() } ?: return 0
        return station.getFare()
    }
}
