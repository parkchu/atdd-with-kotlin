package nextstep.subway.member.domain

class LoginMember(
        val id: Long,

        val email: String,

        val password: String,

        val age: Int
) {
    fun checkPassword(password: String): Boolean {
        return this.password == password
    }

    companion object {
        fun of(member: Member): LoginMember {
            return LoginMember(member.id, member.email, member.password, member.age)
        }
    }
}
