package cupidcrew.backend.api.controller.crew

import cupidcrew.api.backend.exception.BaseException
import cupidcrew.api.backend.exception.BaseResponseCode
import cupidcrew.backend.api.mapper.candidate.CandidateMapper
import cupidcrew.backend.api.model.candidate.CandidateInfoRequestModel
import cupidcrew.backend.api.service.candidate.CandidateService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "[Candidates]", description = "소개팅 당사자 정보 관련 api들")
@RestController
@RequestMapping("/candidates")
class CandidateController (
    private val candidateService: CandidateService,
    private val candidateMapper: CandidateMapper,

) {
    @Operation(summary = "소개팅 당사자 등록", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/new")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun addCandidates(@RequestBody candidateInfoRequestModel: CandidateInfoRequestModel) : ResponseEntity<CandidateInfoResponseModel> {
        if (candidateService.existsCandidate(candidateInfoRequestModel.phoneNumber)) {
            throw BaseException(BaseResponseCode.DUPLICATE_PHONE_NUMBER)
        }

        val candidateDto = candidateMapper.toDto(candidateInfoRequestModel)

        return ResponseEntity.ok(candidateService.createCandidate(candidateDto))

    }
}
