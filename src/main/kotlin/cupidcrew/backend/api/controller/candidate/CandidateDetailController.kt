package cupidcrew.backend.api.controller.candidate

import cupidcrew.backend.api.exception.BaseException
import cupidcrew.backend.api.exception.BaseResponseCode
import cupidcrew.backend.api.mapper.candidate.CandidateMapper
import cupidcrew.backend.api.mapper.crew.CrewMapper
import cupidcrew.backend.api.model.BaseResponseModel
import cupidcrew.backend.api.model.candidate.CandidateInfoRequestModel
import cupidcrew.backend.api.model.candidate.CandidateInfoResponseModel
import cupidcrew.backend.api.model.crew.CrewWithCrewIdResponseModel
import cupidcrew.backend.api.security.JwtTokenUtil
import cupidcrew.backend.api.service.candidate.CandidateDetailService
import cupidcrew.backend.api.service.candidate.CandidateService
import cupidcrew.backend.api.service.crew.CrewService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "[Candidates-detail]", description = "소개팅 각 당사자 관련 기능 api들")
@RestController
@RequestMapping("/candidates/")
class CandidateDetailController(
    private val candidateService: CandidateService,
    private val candidateDetailService: CandidateDetailService,
    private val crewMapper: CrewMapper,
) {

    @Operation(summary = "나의 소개팅 당사자 인기도 증가", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/my/{candidateId}/popularity")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun increaseMyCandidatePopularity(
        @PathVariable candidateId: Long,
    ): BaseResponseModel<String> {
        if (!candidateService.existsCandidateById(candidateId)) {
            throw BaseException(BaseResponseCode.CANDIDATE_NOT_FOUND)
        }

        candidateDetailService.increasePopularity(candidateId)

        return BaseResponseModel(HttpStatus.OK.value(), "popularity+1")
    }

    @Operation(summary = "나의 소개팅 당사자 기회 증가", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/my/{candidateId}/opportunity")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun increaseMyCandidateOpportunity(
        @PathVariable candidateId: Long,
    ): BaseResponseModel<String> {
        if (!candidateService.existsCandidateById(candidateId)) {
            throw BaseException(BaseResponseCode.CANDIDATE_NOT_FOUND)
        }

        candidateDetailService.increaseOpportunity(candidateId)

        return BaseResponseModel(HttpStatus.OK.value(), "opportunity+1")
    }

    @Operation(summary = "소개팅 남녀 id로 소개팅 주선자 id 알아내기", security = [SecurityRequirement(name = "bearerAuth")])
    @GetMapping("/other-crew/{candidateId}")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun getCrewIdBySelectedCandidateId(@PathVariable candidateId: Long): BaseResponseModel<CrewWithCrewIdResponseModel> {
        if (!candidateService.existsCandidateById(candidateId)) {
            throw BaseException(BaseResponseCode.CANDIDATE_NOT_FOUND)
        }

        val crewDto = candidateDetailService.getCrewIdBySelectedCandidateId(candidateId)

        return BaseResponseModel(HttpStatus.OK.value(), crewDto?.let { crewMapper.toModelWithCrewId(it) })

    }
}
