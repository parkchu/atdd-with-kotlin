package nextstep.subway.member.domain

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface MemberRepository : JpaRepository<Member, Long> {
    fun findByEmail(email: String): Optional<Member>
}
