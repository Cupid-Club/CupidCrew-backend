package cupidcrew.backend.api.handler

import cupidcrew.backend.api.exception.BaseException
import cupidcrew.backend.api.model.BaseResponseModel
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BaseException::class)
    fun handleBaseException(ex: BaseException): BaseResponseModel<String> {
        return BaseResponseModel(
            httpStatus = ex.responseCode.code,
            data = ex.message,
        )
    }
}
