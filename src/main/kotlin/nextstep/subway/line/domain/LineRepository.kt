package nextstep.subway.line.domain

import org.springframework.data.jpa.repository.JpaRepository

interface LineRepository : JpaRepository<Line, Long> {
    override fun findAll(): List<Line>
}
