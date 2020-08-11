package nextstep.subway.station.domain

import org.springframework.data.jpa.repository.JpaRepository

interface StationRepository : JpaRepository<Station, Long?> {
    override fun findAll(): List<Station>
}
