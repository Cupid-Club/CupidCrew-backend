package cupidcrew.backend.api.security

import cupidcrew.backend.api.service.admin.BlacklistTokenService
import cupidcrew.backend.api.service.admin.ProjectUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

// SecurityConfig를 생성해 passwordEncoder를 만들고, 앞에서 만들어준 JwtAuthenticationFilter를 등록합니다.
// 로그인과 회원가입 요청 Url을 제외한 나머지는 인증을 받아야 요청 가능하도록 권한을 설정합니다.

@EnableWebSecurity
class SecurityConfig(
    private val blacklistTokenService: BlacklistTokenService,
    private val userDetailsService: ProjectUserDetailsService,
    private val jwtTokenUtil: JwtTokenUtil,
) : WebSecurityConfigurerAdapter() {

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
    }

    @Bean
    fun getPasswordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
//        return NoOpPasswordEncoder.getInstance()
    }

//    override fun configure(http: HttpSecurity) {
//        http
//            .httpBasic().disable() // rest api만 고려, 기본 설정 해제
//            .csrf().disable()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .authorizeRequests()
//            .antMatchers("/swagger-ui/**").permitAll()
//            .antMatchers("/crew/login/**", "/crew/signup").permitAll()
//            .antMatchers("/admin/**").hasRole("ADMIN")
//            .antMatchers("/crew/**").hasAnyRole("CREW", "ADMIN")
////            .anyRequest().authenticated()
//            .and()
////            .addFilterBefore(jwtRequestFilters, UsernamePasswordAuthenticationFilter::class.java)
//            .addFilterBefore(
//                jwtRequestFilters,
//                UsernamePasswordAuthenticationFilter::class.java)
//    }

    override fun configure(http: HttpSecurity) {
        http
            .httpBasic().disable() // rest api만 고려, 기본 설정 해제
            .csrf().disable() // csrf 보안 토큰 disable 처리
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 사용 안함
            .and()
            .authorizeRequests() // 요청에 대한 사용권한 체크
            .antMatchers("/crew/signup/**", "/crew/login/**", "/crew/logout/**", "/crew/find/**","/crew/reset/**").permitAll() // 로그인, 회원가입은 누구나 접근 가능
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/crew/**", "/candidates/**").hasAnyRole("CREW", "ADMIN")
            .and()
            .addFilterBefore(JwtRequestFilters(jwtTokenUtil, blacklistTokenService, userDetailsService), UsernamePasswordAuthenticationFilter::class.java)
    }


    override fun configure(web: WebSecurity) {
        web
            .ignoring()
            .antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**")

    }
}
