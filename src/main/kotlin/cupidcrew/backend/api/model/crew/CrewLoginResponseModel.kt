package cupidcrew.backend.api.model.crew

import org.springframework.http.HttpStatus

data class CrewLoginResponseModel(
    val httpStatus: HttpStatus,
    val token: String,
)
