package cupidcrew.backend.api.model.crew

data class CrewFireBaseTokenResponseModel(
    val crewid: Long,
    val name: String,
    val email: String,
    val firebaseToken: String,
)