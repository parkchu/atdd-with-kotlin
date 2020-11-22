package nextstep.subway.favorite.dto

import nextstep.subway.favorite.domain.Favorite
import nextstep.subway.station.dto.StationResponse

data class FavoriteResponse(
        val id: Long,

        val source: StationResponse,

        val target: StationResponse
) {
    companion object {
        fun of(favorite: Favorite, source: StationResponse, target: StationResponse): FavoriteResponse {
            return FavoriteResponse(favorite.id, source, target)
        }
    }
}
