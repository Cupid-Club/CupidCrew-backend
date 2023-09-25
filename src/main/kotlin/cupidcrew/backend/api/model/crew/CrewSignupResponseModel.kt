package cupidcrew.backend.api.model.crew

data class CrewSignupResponseModel(
    val name: String,
    val email: String,
    val mutualFriend: String,
    val company: String,
)
