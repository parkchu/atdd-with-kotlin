package nextstep.subway.member.dto

import nextstep.subway.member.domain.Member

data class MemberRequest(
        val email: String,

        val password: String,

        val age: Int
) {
    fun toMember(): Member {
        return Member(email, password, age)
    }
}
