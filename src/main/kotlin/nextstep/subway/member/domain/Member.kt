package nextstep.subway.member.domain

import nextstep.subway.core.domain.BaseEntity
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Member(
        var email: String,

        var password: String,

        var age: Int,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0
) : BaseEntity() {
    fun update(member: Member) {
        email = member.email
        password = member.password
        age = member.age
    }
}
