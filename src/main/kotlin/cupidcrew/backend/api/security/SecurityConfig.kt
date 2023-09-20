package cupidcrew.backend.api.security

import cupidcrew.backend.api.service.ProjectUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.ExampleMatcher.NoOpPropertyValueTransformer
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.sql.DataSource

// SecurityConfig를 생성해 passwordEncoder를 만들고, 앞에서 만들어준 JwtAuthenticationFilter를 등록합니다.
// 로그인과 회원가입 요청 Url을 제외한 나머지는 인증을 받아야 요청 가능하도록 권한을 설정합니다.

@EnableWebSecurity
class SecurityConfig(
    private val projectUserDetailsService: ProjectUserDetailsService
) : WebSecurityConfigurerAdapter() {

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(projectUserDetailsService)
    }

    @Bean
    fun getPasswordEncoder(): PasswordEncoder {
//        return BCryptPasswordEncoder()
        return NoOpPasswordEncoder.getInstance()
    }

    override fun configure(http: HttpSecurity) {
//        http
//            .httpBasic().disable() // rest api만 고려, 기본 설정 해제
//            .csrf().disable() // csrf 보안 토큰 disable 처리
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 사용 안함
//            .and()
//            .authorizeRequests() // 요청에 대한 사용권한 체크
//            .antMatchers("/candidate/**").authenticated()
//            .antMatchers("/crew/signup/**", "/crew/login/**", "/crew/logout/**").permitAll() // 로그인, 회원가입은 누구나 접근 가능
//            .and()
//            .addFilterBefore(JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java)

        http
            .csrf().disable() // csrf 보안 토큰 disable 처리
            .authorizeRequests()
            .antMatchers("/authenticate").permitAll()
            .anyRequest().authenticated()
            .antMatchers("/admin").hasRole("ADMIN")
            .antMatchers("/user").hasAnyRole("USER", "ADMIN")
            .antMatchers("/").permitAll()
            .and().formLogin();

    }

    override fun configure(web: WebSecurity) {
        web
            .ignoring()
            .antMatchers("/h2-console/**") // h2-console
    }
}
