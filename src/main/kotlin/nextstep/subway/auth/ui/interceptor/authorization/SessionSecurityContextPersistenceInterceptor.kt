package nextstep.subway.auth.ui.interceptor.authorization

import nextstep.subway.auth.infrastructure.SecurityContext
import nextstep.subway.auth.infrastructure.SecurityContextHolder.SPRING_SECURITY_CONTEXT_KEY
import nextstep.subway.auth.infrastructure.SecurityContextHolder.clearContext
import nextstep.subway.auth.infrastructure.SecurityContextHolder.context
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SessionSecurityContextPersistenceInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val securityContext = request.session.getAttribute(SPRING_SECURITY_CONTEXT_KEY) as SecurityContext?
        if (securityContext != null) {
            context = securityContext
        }
        return true
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {
        clearContext()
    }
}
