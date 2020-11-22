package nextstep.subway.auth.ui.interceptor.authentication

import com.fasterxml.jackson.databind.ObjectMapper
import nextstep.subway.auth.domain.Authentication
import nextstep.subway.auth.domain.AuthenticationToken
import nextstep.subway.auth.dto.TokenResponse
import nextstep.subway.auth.infrastructure.AuthorizationExtractor.extract
import nextstep.subway.auth.infrastructure.AuthorizationType
import nextstep.subway.auth.infrastructure.JwtTokenProvider
import nextstep.subway.auth.ui.domain.AuthenticationConverter
import nextstep.subway.member.application.CustomUserDetailsService
import nextstep.subway.member.domain.LoginMember
import org.springframework.web.servlet.HandlerInterceptor
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenAuthenticationInterceptor(private val userDetailsService: CustomUserDetailsService, private val jwtTokenProvider: JwtTokenProvider) : HandlerInterceptor, AuthenticationConverter {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val authenticationToken = convert(request)
        val authentication = authenticate(authenticationToken)
        val payload: String = ObjectMapper().writeValueAsString(authentication.principal)
        val token = jwtTokenProvider.createToken(payload)
        val tokenResponse = TokenResponse(token)
        val httpSession = request.session
        httpSession.setAttribute(TOKEN_KEY, tokenResponse)
        response.status = HttpServletResponse.SC_OK
        return true
    }

    override fun convert(request: HttpServletRequest): AuthenticationToken {
        val credentials = extract(request, AuthorizationType.BASIC)
        val list = String(Base64.getDecoder().decode(credentials)).split(REGEX)
        return AuthenticationToken(list[0], list[1])
    }

    private fun authenticate(token: AuthenticationToken): Authentication {
        val principal = token.principal
        val userDetails = userDetailsService.loadUserByUsername(principal)
        checkAuthentication(userDetails, token)
        return Authentication(userDetails)
    }

    private fun checkAuthentication(userDetails: LoginMember, token: AuthenticationToken) {
        if (!userDetails.checkPassword(token.credentials)) {
            throw RuntimeException()
        }
    }

    companion object {
        const val REGEX = ":"
        const val TOKEN_KEY = "token_key"
    }
}
