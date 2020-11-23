package nextstep.subway.path.dto

class PathResponse(
        val stations: List<PathStationResponse>,
        val distance: Int,
        val duration: Int,
        val fare: Int
) {
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
