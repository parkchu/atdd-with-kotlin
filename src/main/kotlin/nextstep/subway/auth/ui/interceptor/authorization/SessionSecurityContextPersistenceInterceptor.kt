package nextstep.subway.auth.ui.interceptor.authorization

import nextstep.subway.auth.infrastructure.SecurityContext
import nextstep.subway.auth.infrastructure.SecurityContextHolder.SPRING_SECURITY_CONTEXT_KEY
import nextstep.subway.auth.infrastructure.SecurityContextHolder.context
import nextstep.subway.auth.ui.domain.SecurityContextInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SessionSecurityContextPersistenceInterceptor : SecurityContextInterceptor() {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val securityContext = request.session.getAttribute(SPRING_SECURITY_CONTEXT_KEY) as SecurityContext?
        if (securityContext != null) {
            context = securityContext
        }
        return true
    }
}
