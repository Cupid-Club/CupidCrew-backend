package cupidcrew.backend.api.model

open class BaseResponseModel<T>(
    val httpStatus: Int,
    val data: T? = null,
)
