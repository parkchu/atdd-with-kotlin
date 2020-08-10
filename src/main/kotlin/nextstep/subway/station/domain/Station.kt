package nextstep.subway.station.domain

import nextstep.subway.core.domain.BaseEntity
import javax.persistence.*

@Entity
class Station(
    @Column(unique = true, nullable = false)
    val name: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
) : BaseEntity()
