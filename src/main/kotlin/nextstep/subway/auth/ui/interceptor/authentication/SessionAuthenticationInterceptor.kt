package nextstep.subway.auth.ui.interceptor.authentication

import nextstep.subway.auth.domain.Authentication
import nextstep.subway.auth.domain.AuthenticationToken
import nextstep.subway.auth.infrastructure.SecurityContext
import nextstep.subway.auth.infrastructure.SecurityContextHolder.SPRING_SECURITY_CONTEXT_KEY
import nextstep.subway.member.application.CustomUserDetailsService
import nextstep.subway.member.domain.LoginMember
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SessionAuthenticationInterceptor(private val userDetailsService: CustomUserDetailsService) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val token = convert(request)
        val authentication = authenticate(token)
        val httpSession = request.session
        httpSession.setAttribute(SPRING_SECURITY_CONTEXT_KEY, SecurityContext(authentication))
        response.status = HttpServletResponse.SC_OK
        return false
    }

    fun convert(request: HttpServletRequest): AuthenticationToken {
        val paramMap = request.parameterMap
        val principal = paramMap[USERNAME_FIELD]!![0]
        val credentials = paramMap[PASSWORD_FIELD]!![0]
        return AuthenticationToken(principal, credentials)
    }

    fun authenticate(token: AuthenticationToken): Authentication {
        val principal = token.principal
        val userDetails = userDetailsService.loadUserByUsername(principal)
        checkAuthentication(userDetails, token)
        return Authentication(userDetails)
    }

    private fun checkAuthentication(userDetails: LoginMember, token: AuthenticationToken) {
        if (userDetails == null) {
            throw RuntimeException()
        }
        if (!userDetails.checkPassword(token.credentials)) {
            throw RuntimeException()
        }
    }

    companion object {
        const val USERNAME_FIELD = "username"
        const val PASSWORD_FIELD = "password"
    }
}
