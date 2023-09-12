package cupidcrew.backend.api.dto.crew

data class CrewSignupRequestDto(
    val name: String,
    val email: String,
    var m_password: String? = "",
    val mutualFriend: String,
    val company: String,
)
