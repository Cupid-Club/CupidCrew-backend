package cupidcrew.backend.api.controller.candidate

import cupidcrew.backend.api.dto.crew.CrewSignupRequestDto
import cupidcrew.backend.api.exception.BaseException
import cupidcrew.backend.api.exception.BaseResponseCode
import cupidcrew.backend.api.mapper.candidate.CandidateMapper
import cupidcrew.backend.api.mapper.crew.CrewMapper
import cupidcrew.backend.api.model.BaseResponseModel
import cupidcrew.backend.api.model.candidate.CandidateInfoRequestModel
import cupidcrew.backend.api.model.candidate.CandidateInfoResponseModel
import cupidcrew.backend.api.model.crew.CrewFireBaseTokenResponseModel
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

@Tag(name = "[Candidate-detail]", description = "소개팅 각 당사자 관련 기능 api들")
@RestController
@RequestMapping("/candidate/")
class CandidateDetailController(
    private val candidateService: CandidateService,
    private val candidateDetailService: CandidateDetailService,
    private val crewService: CrewService,
    private val candidateMapper: CandidateMapper,
    private val jwtTokenUtil:  JwtTokenUtil,
    private val crewMapper: CrewMapper,
) {
    @Operation(summary = "나의 소개팅 당사자 조회", security = [SecurityRequirement(name = "bearerAuth")])
    @GetMapping("/my")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun getMyCandidates(
        @RequestHeader("Authorization") token: String,
    ): BaseResponseModel<List<CandidateInfoResponseModel>> {
        val actualToken = token.substring("Bearer ".length)
        val crewEmail = jwtTokenUtil.extractUsername(actualToken)
        val crew = crewService.findCrewByEmail(crewEmail)

        val candidatesDto = candidateDetailService.retrieveMyCandidates(crew)

        return BaseResponseModel(HttpStatus.OK.value(), candidatesDto.map { candidateMapper.toModel(it) })
    }

    @Operation(summary = "나의 소개팅 당사자 정보 수정", security = [SecurityRequirement(name = "bearerAuth")])
    @PutMapping("/my/{candidateId}/revise")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun reviseMyCandidate(
        @PathVariable candidateId: Long,
        @RequestBody candidateInfoRequestModel: CandidateInfoRequestModel,
    ): BaseResponseModel<CandidateInfoResponseModel> {
        if (!candidateService.existsCandidateById(candidateId)) {
            throw BaseException(BaseResponseCode.CANDIDATE_NOT_FOUND)
        }

        val candidateDto = candidateMapper.toDto(candidateInfoRequestModel)

        candidateDetailService.reviseCandidate(candidateDto)

        return BaseResponseModel(HttpStatus.OK.value(), candidateMapper.toModel(candidateDto))
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

        return BaseResponseModel(HttpStatus.OK.value(), "delete.")
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
    fun getCrewIdBySelectedCandidateId(@PathVariable candidateId: Long): BaseResponseModel<CrewFireBaseTokenResponseModel> {
        if (!candidateService.existsCandidateById(candidateId)) {
            throw BaseException(BaseResponseCode.CANDIDATE_NOT_FOUND)
        }

        val crewDto = candidateDetailService.getCrewIdBySelectedCandidateId(candidateId)

        return BaseResponseModel(HttpStatus.OK.value(), crewDto?.let { crewMapper.toModelFireBaseToken(it) })

    }

}