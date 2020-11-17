package nextstep.subway.auth.domain

import nextstep.subway.auth.infrastructure.JwtTokenProvider
import nextstep.subway.auth.infrastructure.SecurityContextHolder.context
import nextstep.subway.auth.ui.interceptor.persistence.TokenSecurityContextPersistenceInterceptor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse

class TokenSecurityContextPersistenceInterceptorTest {
    @Test
    fun tokenSecurityTest() {
        val jwtTokenProvider: JwtTokenProvider = mock(JwtTokenProvider::class.java)
        `when`(jwtTokenProvider.validateToken("jwtToken")).thenReturn(true)
        `when`(jwtTokenProvider.getPayload("")).thenReturn("{\"id\":\"1\",\"email\":\"email@email.com\",\"password\":\"password\",\"age\":\"20\"}")
        val request = MockHttpServletRequest()
        val response = MockHttpServletResponse()
        val interceptor = TokenSecurityContextPersistenceInterceptor(jwtTokenProvider)

        interceptor.preHandle(request, response, Any())

        assertThat(context.authentication).isNotNull
    }

    @Test
    fun tokenSecurityTest2() {
        val jwtTokenProvider: JwtTokenProvider = mock(JwtTokenProvider::class.java)
        `when`(jwtTokenProvider.validateToken("jwtToken")).thenReturn(true)
        `when`(jwtTokenProvider.getPayload("")).thenReturn("{\"email\":\"email@email.com\",\"password\":\"password\",\"age\":\"20\"}")
        val request = MockHttpServletRequest()
        val response = MockHttpServletResponse()
        val interceptor = TokenSecurityContextPersistenceInterceptor(jwtTokenProvider)

        interceptor.preHandle(request, response, Any())

        assertThat(context.authentication).isNull()
    }
}
