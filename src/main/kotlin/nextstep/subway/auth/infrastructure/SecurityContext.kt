package nextstep.subway.auth.infrastructure

import nextstep.subway.auth.domain.Authentication

data class SecurityContext(
        val authentication: Authentication?
) {
    constructor(): this(null)
}
