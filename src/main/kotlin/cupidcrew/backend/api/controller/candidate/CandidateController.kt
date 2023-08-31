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
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "[Candidates]", description = "소개팅 당사자 정보 관련 api들")
@RestController
@RequestMapping("/candidates")
class CandidateController(
    private val candidateService: CandidateService,
    private val candidateDetailService: CandidateDetailService,
    private val candidateMapper: CandidateMapper,
    private val jwtTokenProvider: JwtTokenProvider,

) {
    @Operation(summary = "모든 소개팅 당사자 조회", security = [SecurityRequirement(name = "bearerAuth")])
    @GetMapping("/all")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun getAllCandidates(): ResponseEntity<BaseResponseModel<List<CandidateInfoResponseModel>>> {
        val allCandidatesDto = candidateService.retrieveAllCandidates()
        return ResponseEntity.ok(BaseResponseModel(allCandidatesDto.map { candidateMapper.toModel(it) }))
    }

    @Operation(summary = "SOLO인 소개팅 당사자 조회", security = [SecurityRequirement(name = "bearerAuth")])
    @GetMapping("/solo")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun getSoloCandidates(): ResponseEntity<List<CandidateInfoResponseModel>> {
        val soloCandidatesDto = candidateService.retrieveSoloCandidates()
        return ResponseEntity.ok(soloCandidatesDto.map { candidateMapper.toModel(it) })
    }

    @Operation(summary = "소개팅 당사자 등록", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/new")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun addCandidates(
        @RequestHeader("Authorization") token: String,
        @RequestBody candidateInfoRequestModel: CandidateInfoRequestModel,
    ): ResponseEntity<CandidateInfoResponseModel> {
        if (candidateService.existsCandidateByPhoneNumber(candidateInfoRequestModel.phoneNumber)) {
            throw BaseException(BaseResponseCode.DUPLICATE_PHONE_NUMBER)
        }

        val actualToken = token.substring("Bearer ".length)
        val email = jwtTokenProvider.getUserPk(actualToken)
        val candidateInfoRequestModelWithCrewId = candidateInfoRequestModel.apply {
            this.crewEmail = email
        }

        val candidateDto = candidateMapper.toDto(candidateInfoRequestModelWithCrewId)

        candidateDetailService.createCandidate(candidateDto)

        return ResponseEntity.ok(candidateMapper.toModel(candidateDto))
    }
}
