package cupidcrew.backend.api.dto.crew

data class CrewWithCrewIdResponseDto(
    val crewid: Long,
    val name: String,
    val email: String,
)