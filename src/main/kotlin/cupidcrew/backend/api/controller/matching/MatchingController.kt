package cupidcrew.backend.api.controller.matching

import cupidcrew.backend.api.exception.BaseException
import cupidcrew.backend.api.exception.BaseResponseCode
import cupidcrew.backend.api.mapper.matching.MatchingMapper
import cupidcrew.backend.api.model.BaseResponseModel
import cupidcrew.backend.api.model.candidate.CandidateInfoResponseModel
import cupidcrew.backend.api.model.matching.SendingHistoryRequestModel
import cupidcrew.backend.api.model.matching.SendingHistoryResponseModel
import cupidcrew.backend.api.service.candidate.CandidateService
import cupidcrew.backend.api.service.crew.CrewService
import cupidcrew.backend.api.service.matching.MatchingService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
@Tag(name = "[Crew-matching]", description = "매칭시도 관련 api")
@RestController
@RequestMapping("/matching")
class MatchingController (
    private val crewService: CrewService,
    private val candidateService: CandidateService,
    private val matchingService: MatchingService,
    private val matchingMapper: MatchingMapper,
) {
    @Operation(summary = "다른 소개팅 주선자에게 연락한 기록 남기기", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/send")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun remainSendingHistory(
        @RequestBody sendingHistoryRequestModel: SendingHistoryRequestModel
    ): BaseResponseModel<SendingHistoryResponseModel> {
        if (!crewService.existsById(sendingHistoryRequestModel.senderId)) {
            throw BaseException(BaseResponseCode.DUPLICATE_EMAIL)
        }

        if (!crewService.existsById(sendingHistoryRequestModel.receiverId)) {
            throw BaseException(BaseResponseCode.DUPLICATE_EMAIL)
        }

        if (!candidateService.existsCandidateById(sendingHistoryRequestModel.senderCandidateId)) {
            throw BaseException(BaseResponseCode.CANDIDATE_NOT_FOUND)
        }

        if (!candidateService.existsCandidateById(sendingHistoryRequestModel.receiverCandidateId)) {
            throw BaseException(BaseResponseCode.CANDIDATE_NOT_FOUND)
        }

        val historyDto = matchingMapper.toDto(sendingHistoryRequestModel)

        return BaseResponseModel(HttpStatus.OK.value(), matchingMapper.toModel(matchingService.createHistory(historyDto)))

    }


}