package nextstep.subway.auth.ui

import nextstep.subway.auth.dto.TokenResponse
import nextstep.subway.auth.ui.interceptor.authentication.TokenAuthenticationInterceptor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class LoginController {
    @PostMapping("/login/token")
    fun login(request: HttpServletRequest): ResponseEntity<TokenResponse> {
        val token = request.session.getAttribute(TokenAuthenticationInterceptor.TOKEN_KEY) as TokenResponse
        return ResponseEntity.ok().body(token)
    }
}
