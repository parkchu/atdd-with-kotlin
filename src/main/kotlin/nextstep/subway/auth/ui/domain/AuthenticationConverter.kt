package nextstep.subway.auth.ui.domain

import nextstep.subway.auth.domain.AuthenticationToken
import javax.servlet.http.HttpServletRequest


interface AuthenticationConverter {
    fun convert(request: HttpServletRequest): AuthenticationToken
}
