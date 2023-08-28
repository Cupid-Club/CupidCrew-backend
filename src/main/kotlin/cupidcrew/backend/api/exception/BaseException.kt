package cupidcrew.api.backend.exception

class BaseException(val responseCode: BaseResponseCode) : RuntimeException(responseCode.message)
