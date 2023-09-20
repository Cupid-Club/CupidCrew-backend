package cupidcrew.backend.api.service

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class ProjectUserDetailsService : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        return when (username) {
            "foo" -> User.builder()
                    .username("foo")
                    .password("foo")
                    .roles("ADMIN")  // 여기에 실제 역할을 지정하세요.
                    .build()

            else -> throw UsernameNotFoundException("User not found: $username")
        }
    }

}