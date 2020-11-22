package nextstep.subway.favorite.domain

import org.springframework.data.jpa.repository.JpaRepository

interface FavoriteRepository : JpaRepository<Favorite, Long> {
    fun findByMemberId(memberId: Long): List<Favorite>
}

