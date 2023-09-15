package cupidcrew.backend.api.dto.crew

import com.fasterxml.jackson.annotation.JsonProperty

data class CrewSignupRequestDto(
    val name: String,
    val email: String,
    var m_password: String? = "",
    val mutualFriend: String,
    val company: String,
    @get:JsonProperty("isApproved")
    val isApproved: Boolean?= false,
)
