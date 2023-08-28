package cupidcrew.backend.api.model.user

data class UserLoginRequestModel(
    val email: String,
    val password: String,
)
