package nextstep.subway.line.domain

import javax.persistence.*

@Entity
@Table
class LineStation(
        @Column(nullable = false)
        val stationId: Long,

        var preStationId: Long? = null,

        var distance: Int? = null,

        var duration: Int? = null,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0
) {
        fun updatePreStationTo(preStationId: Long?) {
                this.preStationId == preStationId
        }

        fun isSame(lineStation: LineStation): Boolean {
                return this == lineStation
        }
}
