package nextstep.subway.path.dto

class PathResponse(
        val stations: List<PathStationResponse>,
        val distance: Int,
        val duration: Int,
        fare: Int
) {
    var fare = fare
        private set

    fun updateFare(price: Int) {
        this.fare = price
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
