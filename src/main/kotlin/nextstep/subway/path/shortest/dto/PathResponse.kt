package nextstep.subway.path.shortest.dto

import nextstep.subway.path.shortest.domain.TotalPrice

class PathResponse(
        val stations: List<PathStationResponse>,
        val distance: Int,
        val duration: Int,
        fare: Int
) {
    var fare = fare
        private set

    fun updateFareWithAge(age: Int) {
        fare = TotalPrice.get(distance, fare, age)
    }

    companion object {
        fun of(stations: List<PathStationResponse>, total: List<Int>, price: Int): PathResponse {
            return PathResponse(
                    stations,
                    total.first(),
                    total.last(),
                    price
            )
        }
    }
}
