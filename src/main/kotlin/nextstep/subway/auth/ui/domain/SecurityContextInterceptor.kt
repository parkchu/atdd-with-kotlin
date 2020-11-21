package nextstep.subway.auth.ui.domain

import nextstep.subway.auth.infrastructure.SecurityContextHolder.clearContext
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

abstract class SecurityContextInterceptor : HandlerInterceptor {
    @Throws(Exception::class)
    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {
        clearContext()
    }
}
