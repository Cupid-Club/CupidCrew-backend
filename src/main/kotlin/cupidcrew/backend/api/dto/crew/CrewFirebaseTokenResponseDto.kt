package cupidcrew.backend.api.dto.crew

data class CrewFirebaseTokenResponseDto(
    val crewid: Long,
    val name: String,
    val email: String,
    val fireBaseToken: String,
)
