package cupidcrew.backend.api.service.admin

import cupidcrew.backend.api.service.crew.CrewService
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class ProjectUserDetailsService(
    private val crewService: CrewService,
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val crew = crewService.findCrewByEmail(email)
        val authorities: MutableSet<SimpleGrantedAuthority> = mutableSetOf()

        when {
            email == "admin@naver.com" -> {
                authorities.add(SimpleGrantedAuthority("ROLE_CREW"))
                authorities.add(SimpleGrantedAuthority("ROLE_ADMIN"))
            }
            crew.role.authority == "ROLE_ADMIN" -> authorities.add(SimpleGrantedAuthority("ROLE_ADMIN"))
            crew.role.authority == "ROLE_CREW" -> authorities.add(SimpleGrantedAuthority("ROLE_CREW"))
            else -> throw IllegalArgumentException("Unknown role")
        }

        return User(crew.email, crew.m_password, authorities)

    }
}