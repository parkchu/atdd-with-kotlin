package nextstep.subway.auth.infrastructure

import org.apache.logging.log4j.util.Strings
import javax.servlet.http.HttpServletRequest

object AuthorizationExtractor {
    const val AUTHORIZATION = "Authorization"
    val ACCESS_TOKEN_TYPE = AuthorizationExtractor::class.java.simpleName + ".ACCESS_TOKEN_TYPE"

    fun extract(request: HttpServletRequest, type: AuthorizationType): String {
        val typeToLowerCase: String = type.toLowerCase()
        val typeLength = typeToLowerCase.length
        val headers = request.getHeaders(AUTHORIZATION)
        while (headers.hasMoreElements()) {
            val value = headers.nextElement()
            if (value.toLowerCase().startsWith(typeToLowerCase)) {
                var authHeaderValue = value.substring(typeLength).trim { it <= ' ' }
                request.setAttribute(ACCESS_TOKEN_TYPE, value.substring(0, typeLength).trim { it <= ' ' })
                val commaIndex = authHeaderValue.indexOf(',')
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex)
                }
                return authHeaderValue
            }
        }
        return Strings.EMPTY
    }
}
