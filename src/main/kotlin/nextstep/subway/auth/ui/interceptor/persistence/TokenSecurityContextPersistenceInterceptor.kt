package nextstep.subway.auth.ui.interceptor.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import nextstep.subway.auth.domain.Authentication
import nextstep.subway.auth.infrastructure.AuthorizationExtractor.extract
import nextstep.subway.auth.infrastructure.AuthorizationType
import nextstep.subway.auth.infrastructure.JwtTokenProvider
import nextstep.subway.auth.infrastructure.SecurityContext
import nextstep.subway.auth.infrastructure.SecurityContextHolder.context
import nextstep.subway.member.domain.LoginMember
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenSecurityContextPersistenceInterceptor(private val jwtTokenProvider: JwtTokenProvider) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (context.authentication != null) {
            return true
        }

        val credentials = extract(request, AuthorizationType.BEARER)
        if (!jwtTokenProvider.validateToken(credentials)) {
            return true
        }

        val securityContext = extractSecurityContext(credentials)
        if (securityContext != null) {
            context = securityContext
        }
        return true
    }

    private fun extractSecurityContext(credentials: String): SecurityContext? {
        return try {
            val payload = jwtTokenProvider.getPayload(credentials)
            val hashMap = ObjectMapper().readValue(payload, HashMap::class.java)
            val map = hashMap.map { it.key.toString() to it.value.toString() }.toMap()
            val loginMember = LoginMember(map["id"]!!.toLong(), map["email"]!!, map["password"]!!, map["age"]!!.toInt())
            SecurityContext(Authentication(loginMember))
        } catch (e: java.lang.Exception) {
            null
        }
    }
}
