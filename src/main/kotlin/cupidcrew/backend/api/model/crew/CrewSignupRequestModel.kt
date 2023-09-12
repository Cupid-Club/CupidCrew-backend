package cupidcrew.backend.api.model.crew

data class CrewSignupRequestModel(
    val name: String,
    val email: String,
    var password: String,
    val mutualFriend: String,
    val company: String,
)
