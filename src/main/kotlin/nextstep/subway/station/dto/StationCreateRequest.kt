package nextstep.subway.station.dto

import nextstep.subway.station.domain.Station

data class StationCreateRequest(val name: String) {
    fun toStation(): Station = Station(name)
}
