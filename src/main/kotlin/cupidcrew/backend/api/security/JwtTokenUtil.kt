package cupidcrew.backend.api.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.HttpServletRequest

@Service
class JwtTokenUtil {
    
    private var SECRET_KEY = "thisiscupidcrewprojectwhichisthebestapplicationintheworld"

    fun generateToken(userDetails: UserDetails) : String {
        val claims = mutableMapOf<String, Any>()
        return createToken(claims, userDetails)
    }

    private fun createToken(claims: Map<String, Any>, userDetails: UserDetails): String {
        val now = Date()
        val tokenValidTime =  (90 * 24 * 60 * 60 * 1000L)
        val updatedClaims = claims.toMutableMap().apply {
            put("authorities", userDetails.authorities.map { it.authority })
        }

        return Jwts.builder()
                .setClaims(updatedClaims)
                .setSubject(userDetails.username)
                .setIssuedAt(now)
                .setExpiration(Date(now.time + tokenValidTime))// set Expire Time
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 사용할 암호화 알고리즘과
                .compact()

    }

    private fun extractClaim(token: String): Claims {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).body
    }


    fun extractUsername(token: String) : String {
        return extractClaim(token).subject
    }

    fun extractExpiration(token: String) : Date {
        return extractClaim(token).expiration
    }

    private fun isTokenExpired(token: String) : Boolean {
        return extractExpiration(token).before(Date())
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    fun resolveToken(request: HttpServletRequest): String? {
        return request.getHeader("Authorization")
    }


    fun validateToken(token: String, userDetails: UserDetails) : Boolean {
        val username = extractUsername(token)
        return (username == userDetails.username) && !isTokenExpired(token)
    }

}