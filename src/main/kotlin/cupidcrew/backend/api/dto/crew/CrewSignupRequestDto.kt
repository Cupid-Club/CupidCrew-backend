package cupidcrew.backend.api.dto.crew

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class CrewSignupRequestDto(
    val name: String,
    val email: String,
    var m_password: String? = "",
    val mutualFriend: String,
    val company: String,
    val firebaseToken: String,
    var approved: Boolean? = true,
    var role: String? = "ROLE_USER",
)
