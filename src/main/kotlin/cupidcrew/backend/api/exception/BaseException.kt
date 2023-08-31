package cupidcrew.backend.api.exception

class BaseException(val responseCode: BaseResponseCode) : RuntimeException(responseCode.message)
