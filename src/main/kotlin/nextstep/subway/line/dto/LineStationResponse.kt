package nextstep.subway.line.dto

import nextstep.subway.line.domain.LineStation
import nextstep.subway.station.domain.StationRepository
import nextstep.subway.station.dto.StationResponse

data class LineStationResponse(val station: StationResponse, val preStationId: Long?, val distance: Int?, val duration: Int?) {
    companion object {
        fun of(lineStation: LineStation, stationRepository: StationRepository): LineStationResponse {
            return LineStationResponse(
                    StationResponse.of(stationRepository.findById(lineStation.stationId).orElseThrow { RuntimeException() }),
                    lineStation.preStationId,
                    lineStation.distance,
                    lineStation.duration)
        }
    }
}
