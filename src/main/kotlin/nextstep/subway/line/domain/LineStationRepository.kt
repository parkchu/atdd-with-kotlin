package nextstep.subway.line.domain

import org.springframework.data.jpa.repository.JpaRepository

interface LineStationRepository: JpaRepository<LineStation, Long> {
    override fun findAll(): List<LineStation>
}
