package nextstep.subway.favorite.application

import nextstep.subway.favorite.domain.Favorite
import nextstep.subway.favorite.domain.FavoriteRepository
import nextstep.subway.favorite.dto.FavoriteRequest
import nextstep.subway.favorite.dto.FavoriteResponse
import nextstep.subway.station.domain.Station
import nextstep.subway.station.domain.StationRepository
import nextstep.subway.station.dto.StationResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FavoriteService @Autowired constructor(
        private val favoriteRepository: FavoriteRepository, private val stationRepository: StationRepository
) {
    fun createFavorite(request: FavoriteRequest, memberId: Long): Long {
        val favorite = Favorite(request.source, request.target, memberId)
        favoriteRepository.save(favorite)
        return favorite.id
    }

    fun deleteFavorite(id: Long) {
        favoriteRepository.deleteById(id)
    }

    fun findMyFavorites(memberId: Long): List<FavoriteResponse> {
        val favorites = favoriteRepository.findByMemberId(memberId)
        val stations = extractStations(favorites)
        return favorites.map { it: Favorite ->
            FavoriteResponse.of(
                    it,
                    StationResponse.of(stations.getValue(it.sourceStationId)),
                    StationResponse.of(stations.getValue(it.targetStationId)))
        }

    }

    private fun extractStations(favorites: List<Favorite>): Map<Long, Station> {
        val stationIds = extractStationIds(favorites)
        return stationRepository.findAllById(stationIds)
                .map { it.id to it }.toMap()
    }

    private fun extractStationIds(favorites: List<Favorite>): Set<Long> {
        val stationIds: MutableSet<Long> = HashSet()
        for (favorite in favorites) {
            stationIds.add(favorite.sourceStationId)
            stationIds.add(favorite.targetStationId)
        }
        return stationIds
    }
}
