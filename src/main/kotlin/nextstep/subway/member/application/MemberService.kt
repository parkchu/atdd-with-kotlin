package nextstep.subway.member.application

import nextstep.subway.member.domain.MemberRepository
import nextstep.subway.member.dto.MemberRequest
import nextstep.subway.member.dto.MemberResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberService @Autowired constructor(
        private val memberRepository: MemberRepository
) {
    fun createMember(request: MemberRequest): MemberResponse {
        val member = memberRepository.save(request.toMember())
        return MemberResponse.of(member)
    }

    fun findMember(id: Long): MemberResponse {
        val member = memberRepository.findById(id).orElseThrow { RuntimeException() }
        return MemberResponse.of(member)
    }

    fun updateMember(id: Long, param: MemberRequest) {
        val member = memberRepository.findById(id).orElseThrow { RuntimeException() }
        member.update(param.toMember())
    }

    fun deleteMember(id: Long) {
        memberRepository.deleteById(id)
    }
}
