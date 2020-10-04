package nextstep.subway.line.dto

import nextstep.subway.line.domain.LineStation
import nextstep.subway.station.domain.Station
import nextstep.subway.station.domain.StationRepository
import nextstep.subway.station.dto.StationResponse

data class LineStationResponse(val station: StationResponse, val preStationId: Long?, val distance: Int?, val duration: Int?) {
    companion object {
        fun of(lineStation: LineStation, stations: List<Station>): LineStationResponse {
            return LineStationResponse(
                    StationResponse.of(stations.find { station -> station.id == lineStation.stationId } ?: throw RuntimeException()),
                    lineStation.preStationId,
                    lineStation.distance,
                    lineStation.duration)
        }
    }
}
