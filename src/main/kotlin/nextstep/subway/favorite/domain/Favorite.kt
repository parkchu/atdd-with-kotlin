package nextstep.subway.favorite.domain

import nextstep.subway.core.domain.BaseEntity
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Favorite(
        val sourceStationId: Long,

        val targetStationId: Long,

        val memberId: Long,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0
) : BaseEntity()
