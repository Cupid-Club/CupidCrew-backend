package cupidcrew.backend.api.model.candidate

import org.springframework.http.HttpStatus

data class CandidateInfoResponseModel(
    val httpStatus: HttpStatus,
    val msg: String = "등록 완료"
)