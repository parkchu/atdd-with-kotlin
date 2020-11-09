package nextstep.subway.path.domain

import nextstep.subway.path.dto.PathStationResponse

class Path(val stations: List<PathStation>) {
    val distance
        get() = stations.sumBy { it.distance }
    val duration
        get() = stations.sumBy { it.duration }

    fun add(path: Path): Path {
        val lineStations = stations.toMutableList()
        lineStations.add(path.stations.last())
        return Path(lineStations)
    }

    fun getStationsResponse(): List<PathStationResponse> {
        return stations.map { PathStationResponse.of(it.station) }
    }
}
