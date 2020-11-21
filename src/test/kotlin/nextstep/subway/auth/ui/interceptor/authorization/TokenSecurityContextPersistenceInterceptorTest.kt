package nextstep.subway.auth.ui.interceptor.authorization

import nextstep.subway.auth.infrastructure.JwtTokenProvider
import nextstep.subway.auth.infrastructure.SecurityContextHolder
import nextstep.subway.auth.infrastructure.SecurityContextHolder.context
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse

class TokenSecurityContextPersistenceInterceptorTest {
    @BeforeEach
    fun setUp() {
        SecurityContextHolder.clearContext()
    }

    @Test
    fun validSecurityToken() {
        val jwtTokenProvider: JwtTokenProvider = mock(JwtTokenProvider::class.java)
        `when`(jwtTokenProvider.validateToken(anyString())).thenReturn(true)
        `when`(jwtTokenProvider.getPayload("")).thenReturn("{\"id\":\"1\",\"email\":\"email@email.com\",\"password\":\"password\",\"age\":\"20\"}")
        val request = MockHttpServletRequest()
        val response = MockHttpServletResponse()
        val interceptor = TokenSecurityContextPersistenceInterceptor(jwtTokenProvider)

        interceptor.preHandle(request, response, Any())

        assertThat(context.authentication).isNotNull
    }

    @Test
    fun invalidSecurityToken() {
        val jwtTokenProvider: JwtTokenProvider = mock(JwtTokenProvider::class.java)
        `when`(jwtTokenProvider.validateToken(anyString())).thenReturn(false)
        val request = MockHttpServletRequest()
        val response = MockHttpServletResponse()
        val interceptor = TokenSecurityContextPersistenceInterceptor(jwtTokenProvider)

        interceptor.preHandle(request, response, Any())

        assertThat(context.authentication).isNull()
    }
}
