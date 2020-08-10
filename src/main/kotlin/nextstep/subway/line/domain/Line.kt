package nextstep.subway.line.domain

import nextstep.subway.core.domain.BaseEntity
import java.time.LocalTime
import javax.persistence.*

@Entity
class Line(
        @Column(unique = true, nullable = false)
        val name: String,

        val color: String,

        val startTime: LocalTime,

        val endTime: LocalTime,

        val intervalTime: Int,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0
) : BaseEntity()
