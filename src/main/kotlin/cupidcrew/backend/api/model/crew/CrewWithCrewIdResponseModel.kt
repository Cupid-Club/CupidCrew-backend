package cupidcrew.backend.api.model.crew

data class CrewWithCrewIdResponseModel(
    val crewid: Long,
    val name: String,
    val email: String,
)