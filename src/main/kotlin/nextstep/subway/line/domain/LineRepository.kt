package nextstep.subway.line.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface LineRepository : JpaRepository<Line, Long> {
    override fun findAll(): List<Line>

    @Query("select * from line where id in (select line_id from line_station where station_id = :station_id)", nativeQuery = true)
    fun findByStationContains(@Param("station_id") stationId: Long): List<Line>?
}
