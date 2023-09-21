package cupidcrew.backend.api.security

import cupidcrew.backend.api.service.admin.BlacklistTokenService
import cupidcrew.backend.api.service.admin.ProjectUserDetailsService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilters(
    private val jwtTokenUtil: JwtTokenUtil,
    private val blacklistTokenService: BlacklistTokenService,
    private val userDetailsService: ProjectUserDetailsService
) : GenericFilterBean() {
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val headerValue = (request as HttpServletRequest).getHeader("Authorization")
        println("Header Value: $headerValue")
        val token: String? = headerValue?.substring(7)
        // 유효한 토큰인지 확인합니다.

        if (token != null) {
            if (blacklistTokenService.isBlacklisted(token)) {
                throw SecurityException("This token has been blacklisted")
            }
            val userDetails = userDetailsService.loadUserByUsername(jwtTokenUtil.extractUsername(token))

            if (jwtTokenUtil.validateToken(token, userDetails)) {
                val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        chain.doFilter(request, response)
    }
}