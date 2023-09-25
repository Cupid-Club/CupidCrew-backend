package cupidcrew.backend.api.dto.crew

import org.springframework.security.core.authority.SimpleGrantedAuthority

data class CrewSignupResponseDto(
    val name: String,
    val email: String,
    var password: String,
    val mutualFriend: String,
    val company: String,
    val approved: Boolean,
    val role : SimpleGrantedAuthority,

)
