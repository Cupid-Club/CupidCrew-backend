package cupidcrew.backend.api.model.crew

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class CrewSignupRequestModel(
    val name: String,
    val email: String,
    var password: String,
    val mutualFriend: String,
    val company: String,
    val fireBaseToken: String,
)
