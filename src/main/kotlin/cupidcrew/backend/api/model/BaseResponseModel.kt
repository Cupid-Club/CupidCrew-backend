package cupidcrew.backend.api.model

import org.springframework.http.HttpStatus

open class BaseResponseModel<T>(
    val httpStatus: HttpStatus,
    val data: T? = null,
)
