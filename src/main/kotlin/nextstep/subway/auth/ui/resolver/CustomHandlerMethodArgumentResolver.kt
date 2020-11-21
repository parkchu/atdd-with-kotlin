package nextstep.subway.auth.ui.resolver

import nextstep.subway.auth.infrastructure.SecurityContextHolder.context
import nextstep.subway.member.domain.LoginMember
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import java.lang.RuntimeException


@Component
class CustomHandlerMethodArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(methodParameter: MethodParameter): Boolean {
        return methodParameter.parameterType == LoginMember::class.java
    }

    @Throws(Exception::class)
    override fun resolveArgument(methodParameter: MethodParameter, modelAndViewContainer: ModelAndViewContainer?, nativeWebRequest: NativeWebRequest, webDataBinderFactory: WebDataBinderFactory?): Any {
        val authentication = context.authentication ?: throw RuntimeException("")
        return authentication.principal as LoginMember
    }
}
