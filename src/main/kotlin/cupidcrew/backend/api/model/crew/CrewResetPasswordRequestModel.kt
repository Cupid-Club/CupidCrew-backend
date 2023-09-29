package cupidcrew.backend.api.model.crew

data class CrewResetPasswordRequestModel(
    val name: String,
    val email: String,
    val newPassword: String,
)
