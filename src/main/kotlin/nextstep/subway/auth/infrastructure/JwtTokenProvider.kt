package nextstep.subway.auth.infrastructure

import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider {
    @Value("\${security.jwt.token.secret-key}")
    private val secretKey: String? = null

    @Value("\${security.jwt.token.expire-length}")
    private val validityInMilliseconds: Long = 0

    fun createToken(payload: String?): String {
        val claims: Claims = Jwts.claims().setSubject(payload)
        val now = Date()
        val validity = Date(now.time + validityInMilliseconds)
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact()
    }

    fun getPayload(token: String?): String {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject()
    }

    fun validateToken(token: String?): Boolean {
        return try {
            val claims: Jws<Claims> = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            if (claims.getBody().getExpiration().before(Date())) {
                false
            } else true
        } catch (e: JwtException) {
            false
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}
