package cupidcrew.backend.api.dto.crew

data class CrewSignupRequestDto(
    val name: String,
    val email: String,
    var password: String,
)
