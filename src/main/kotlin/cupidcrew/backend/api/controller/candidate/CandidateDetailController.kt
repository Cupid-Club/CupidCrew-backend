package cupidcrew.backend.api.controller.candidate

import cupidcrew.backend.api.exception.BaseException
import cupidcrew.backend.api.exception.BaseResponseCode
import cupidcrew.backend.api.mapper.candidate.CandidateMapper
import cupidcrew.backend.api.model.BaseResponseModel
import cupidcrew.backend.api.model.candidate.CandidateInfoRequestModel
import cupidcrew.backend.api.model.candidate.CandidateInfoResponseModel
import cupidcrew.backend.api.security.JwtTokenProvider
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
    private val crewService: CrewService,
    private val candidateMapper: CandidateMapper,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    @Operation(summary = "나의 소개팅 당사자 조회", security = [SecurityRequirement(name = "bearerAuth")])
    @GetMapping("/my")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun getMyCandidates(
        @RequestHeader("Authorization") token: String,
    ): BaseResponseModel<List<CandidateInfoResponseModel>> {
        val actualToken = token.substring("Bearer ".length)
        val crewEmail = jwtTokenProvider.getUserPk(actualToken)
        val crew = crewService.findCrew(crewEmail)

        val candidatesDto = candidateDetailService.retrieveMyCandidates(crew)

        return BaseResponseModel(HttpStatus.OK, candidatesDto.map { candidateMapper.toModel(it) })
    }

    @Operation(summary = "나의 소개팅 당사자 정보 수정", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/my/{candidateId}/revise")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun reviseMyCandidate(
        @PathVariable candidateId: Long,
        @RequestBody candidateInfoRequestModel: CandidateInfoRequestModel,
    ): BaseResponseModel<CandidateInfoResponseModel> {
        if (!candidateService.existsCandidateById(candidateId)) {
            throw BaseException(BaseResponseCode.CANDIDATE_NOT_FOUND)
        }

        val candidateDto = candidateMapper.toDto(candidateInfoRequestModel)

        candidateDetailService.createCandidate(candidateDto)

        return BaseResponseModel(HttpStatus.OK, candidateMapper.toModel(candidateDto))
    }

    @Operation(summary = "나의 소개팅 당사자 삭제", security = [SecurityRequirement(name = "bearerAuth")])
    @DeleteMapping("/my/{candidateId}")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun deleteMyCandidate(
        @PathVariable candidateId: Long,
    ): BaseResponseModel<String> {
        if (!candidateService.existsCandidateById(candidateId)) {
            throw BaseException(BaseResponseCode.CANDIDATE_NOT_FOUND)
        }

        candidateDetailService.deleteCandidate(candidateId)

        return BaseResponseModel(HttpStatus.OK, "delete.")
    }

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

        return BaseResponseModel(HttpStatus.OK, "popularity+1")
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

        return BaseResponseModel(HttpStatus.OK, "opportunity+1")
    }
}
