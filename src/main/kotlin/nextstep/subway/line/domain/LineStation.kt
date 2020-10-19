package nextstep.subway.line.domain

import javax.persistence.*

@Entity
@Table(name = "line_station")
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
        fun isSame(lineStation: LineStation): Boolean {
                return this.stationId == lineStation.stationId
        }

        fun updatePreStationTo(id: Long?) {
                preStationId = id
        }
}
