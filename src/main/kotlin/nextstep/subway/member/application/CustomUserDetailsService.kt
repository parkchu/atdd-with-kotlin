package nextstep.subway.member.application

import nextstep.subway.member.domain.LoginMember
import nextstep.subway.member.domain.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class CustomUserDetailsService @Autowired constructor(
        private val memberRepository: MemberRepository
) {
    fun loadUserByUsername(email: String): LoginMember {
        val member = memberRepository.findByEmail(email).orElseThrow { RuntimeException() }
        return LoginMember.of(member)
    }
}
