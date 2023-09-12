package cupidcrew.backend.api

import cupidcrew.backend.api.security.JwtTokenProvider
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.* // ktlint-disable no-wildcard-imports
import org.junit.jupiter.api.Test
import org.springframework.security.core.userdetails.UserDetailsService

internal class JWTUtilTest {

    private val userDetailsService: UserDetailsService = mockk() // mockk 라이브러리를 사용하여 mock 객체를 생성
    private val jwtTokenProvider = JwtTokenProvider(userDetailsService)

    @Test
    fun `Jwt Token 발행과 검증시 subject에 userPK가 포함되어 있어야 한다`() {
        // given
        val userPk = "aisdbrud@naver.com"

        // when
        val result = jwtTokenProvider.createToken(userPk)
        val userPKFromToken = jwtTokenProvider.getUserPk(result)

        // then
        assertNotNull(result)
        assertEquals(userPk, userPKFromToken)
    }

    @Test
    fun `유효하지 않은 토큰 Jwt Token 검증에 실패하여 MalformedJwtException을 반환한다`() {
        // given
        val randomToken = "a.b.c"

        // when & then
        assertThrows(MalformedJwtException::class.java) {
            jwtTokenProvider.getUserPk(randomToken)
        }
    }

    @Test
    fun `만료기간이 지난 토큰은 검증시 ExpiredJwtException을 반환한다`() {
        // given
        val userPK = "aisdbrud@naver.com"
        val expireTime = 1L
        val result = jwtTokenProvider.createToken(userPK, expireTime)

        // when
        Thread.sleep(10L)

        // then
        assertThrows(ExpiredJwtException::class.java) {
            jwtTokenProvider.getUserPk(result)
        }
    }
}
