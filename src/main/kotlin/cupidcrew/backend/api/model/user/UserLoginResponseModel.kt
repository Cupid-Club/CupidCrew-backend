package cupidcrew.backend.api.model.user

import org.springframework.http.HttpStatus

data class UserLoginResponseModel(
    val httpStatus: HttpStatus,
    val token: String,
)
