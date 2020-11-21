package nextstep.subway.config

import nextstep.subway.auth.infrastructure.JwtTokenProvider
import nextstep.subway.auth.ui.interceptor.authentication.SessionAuthenticationInterceptor
import nextstep.subway.auth.ui.interceptor.authentication.TokenAuthenticationInterceptor
import nextstep.subway.auth.ui.interceptor.authorization.SessionSecurityContextPersistenceInterceptor
import nextstep.subway.auth.ui.interceptor.authorization.TokenSecurityContextPersistenceInterceptor
import nextstep.subway.auth.ui.resolver.CustomHandlerMethodArgumentResolver
import nextstep.subway.member.application.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class WebMvcConfig(private val userDetailsService: CustomUserDetailsService, private val jwtTokenProvider: JwtTokenProvider) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(SessionAuthenticationInterceptor(userDetailsService)).addPathPatterns("/login/session")
        registry.addInterceptor(SessionSecurityContextPersistenceInterceptor())
        registry.addInterceptor(TokenAuthenticationInterceptor(userDetailsService, jwtTokenProvider)).addPathPatterns("/login/token")
        registry.addInterceptor(TokenSecurityContextPersistenceInterceptor(jwtTokenProvider)).addPathPatterns("/members/me")
    }

    @Autowired
    private val customHandlerMethodArgumentResolver: CustomHandlerMethodArgumentResolver? = null

    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver?>) {
        argumentResolvers.add(customHandlerMethodArgumentResolver)
    }
}
