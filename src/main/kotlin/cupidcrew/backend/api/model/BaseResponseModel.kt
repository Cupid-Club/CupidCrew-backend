package cupidcrew.backend.api.model

open class BaseResponseModel<T>(
    val data: T? = null,
    val msg: String? = null
) {
    companion object {
        fun <T> ofData(data: T): BaseResponseModel<T> = BaseResponseModel(data)
    }
}
