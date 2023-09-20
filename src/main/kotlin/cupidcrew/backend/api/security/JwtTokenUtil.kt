package cupidcrew.backend.api.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtTokenUtil {
    
    private var SECRET_KEY = "thisiscupidcrewprojectwhichisthebestapplicationintheworld"

    fun generateToken(userDetails: UserDetails) : String {
        val claims = mutableMapOf<String, Any>()
        return createToken(claims, userDetails.username)
    }

    private fun createToken(claims: Map<String, Any>, subject: String): String {
        val now = Date()
        val tokenValidTime =  (90 * 24 * 60 * 60 * 1000L)
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
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

    fun validateToken(token: String, userDetails: UserDetails) : Boolean {
        val username = extractUsername(token)
        return (username == userDetails.username) && !isTokenExpired(token)
    }

}