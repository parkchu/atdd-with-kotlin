package nextstep.subway.auth.domain

import nextstep.subway.auth.dto.TokenResponse
import nextstep.subway.auth.infrastructure.JwtTokenProvider
import nextstep.subway.auth.infrastructure.SecurityContextHolder.SPRING_SECURITY_CONTEXT_KEY
import nextstep.subway.auth.ui.interceptor.authentication.TokenAuthenticationInterceptor
import nextstep.subway.auth.ui.interceptor.authentication.TokenAuthenticationInterceptor.Companion.REGEX
import nextstep.subway.member.application.CustomUserDetailsService
import nextstep.subway.member.domain.LoginMember
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import java.util.*

@ExtendWith(MockitoExtension::class)
class TokenAuthenticationInterceptorTest {
    @Mock
    private lateinit var userDetailsService: CustomUserDetailsService

    @Test
    fun getLoginInformation() {
        val jwtTokenProvider: JwtTokenProvider = mock(JwtTokenProvider::class.java)
        `when`(jwtTokenProvider.createToken(anyString())).thenReturn("jwtToken")
        `when`(userDetailsService.loadUserByUsername(EMAIL)).thenReturn(LoginMember(1, EMAIL, PASSWORD, 18))
        val interceptor = TokenAuthenticationInterceptor(userDetailsService, jwtTokenProvider)
        val request = MockHttpServletRequest()
        val targetBytes: ByteArray = (EMAIL + REGEX + PASSWORD).toByteArray()
        val encodedBytes = Base64.getEncoder().encode(targetBytes)
        val credentials = String(encodedBytes)
        request.addHeader("Authorization", "Basic $credentials");
        val response = MockHttpServletResponse()

        interceptor.preHandle(request, response, Any())

        val token = request.session!!.getAttribute(SPRING_SECURITY_CONTEXT_KEY) as TokenResponse
        assertThat(token.accessToken).isEqualTo("jwtToken")
    }

    companion object {
        const val EMAIL = "test@test.com"
        const val PASSWORD = "password"
    }
}
