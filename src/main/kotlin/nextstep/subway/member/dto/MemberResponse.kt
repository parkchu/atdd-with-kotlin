package nextstep.subway.member.dto

import nextstep.subway.member.domain.LoginMember
import nextstep.subway.member.domain.Member

data class MemberResponse(
        val id: Long,

        val email: String,

        val age: Int
) {
    companion object {
        fun of(member: Member): MemberResponse {
            return MemberResponse(member.id, member.email, member.age)
        }

        fun of(loginMember: LoginMember): MemberResponse {
            return MemberResponse(loginMember.id, loginMember.email, loginMember.age)
        }
    }
}
