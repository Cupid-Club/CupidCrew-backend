package cupidcrew.backend.api.model.user

data class UserSignupRequestModel(
    val name: String,
    val email: String,
    var password: String,
)
