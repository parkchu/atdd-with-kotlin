package nextstep.subway.auth.infrastructure

enum class AuthorizationType {
    BASIC,
    BEARER;

    fun toLowerCase(): String {
        return name.toLowerCase()
    }
}
