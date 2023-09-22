package cupidcrew.backend.api.controller.candidate

import cupidcrew.backend.api.exception.BaseException
import cupidcrew.backend.api.exception.BaseResponseCode
import cupidcrew.backend.api.mapper.candidate.CandidateMapper
import cupidcrew.backend.api.model.BaseResponseModel
import cupidcrew.backend.api.model.candidate.CandidateInfoRequestModel
import cupidcrew.backend.api.model.candidate.CandidateInfoResponseModel
import cupidcrew.backend.api.security.JwtTokenUtil
import cupidcrew.backend.api.service.candidate.CandidateDetailService
import cupidcrew.backend.api.service.candidate.CandidateService
import cupidcrew.backend.api.service.candidate.FileStorageService
import cupidcrew.backend.api.service.crew.CrewService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
@Tag(name = "[Candidates]", description = "소개팅 당사자 정보 관련 api들")
@RestController
@RequestMapping("/candidates")
class CandidateController(
    private val crewService: CrewService,
    private val candidateService: CandidateService,
    private val candidateDetailService: CandidateDetailService,
    private val candidateMapper: CandidateMapper,
    private val jwtTokenUtil: JwtTokenUtil,
    private val fileStorageService: FileStorageService,

    ) {
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "모든 소개팅 당사자 조회", security = [SecurityRequirement(name = "bearerAuth")])
    @GetMapping("/all")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun getAllCandidates(
        @RequestHeader("Authorization") token: String,
    ): BaseResponseModel<List<CandidateInfoResponseModel>> {
        val actualToken = token.substring("Bearer ".length)
        val crewEmail = jwtTokenUtil.extractUsername(actualToken)
        val crew = crewService.findCrewByEmail(crewEmail)

        if (candidateDetailService.retrieveMyCandidates(crew).isEmpty()) {
            throw BaseException(BaseResponseCode.LIMIT_QUALIFICATION_NO_CANDIDATE_REGISTERED)
        }

        val candidatesDto = candidateService.retrieveAllCandidates()

        return BaseResponseModel(HttpStatus.OK.value(), candidatesDto.map { candidateMapper.toModel(it) })
    }

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "Single인 소개팅 당사자 조회", security = [SecurityRequirement(name = "bearerAuth")])
    @GetMapping("/single")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun getSingleCandidates(
        @RequestHeader("Authorization") token: String,
    ): BaseResponseModel<List<CandidateInfoResponseModel>> {
        val actualToken = token.substring("Bearer ".length)
        val crewEmail = jwtTokenUtil.extractUsername(actualToken)
        val crew = crewService.findCrewByEmail(crewEmail)

        if (candidateDetailService.retrieveMyCandidates(crew).isEmpty()) {
            throw BaseException(BaseResponseCode.LIMIT_QUALIFICATION_NO_CANDIDATE_REGISTERED)
        }

        val candidatesDto = candidateService.retrieveSingleCandidates()
        return BaseResponseModel(HttpStatus.OK.value(), candidatesDto.map { candidateMapper.toModel(it) })
    }

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "소개팅 당사자 등록", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/new")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun addCandidates(
        @RequestHeader("Authorization") token: String,
        @RequestBody candidateInfoRequestModel: CandidateInfoRequestModel,
    ): BaseResponseModel<Long> {
        if (candidateService.existsCandidateByPhoneNumber(candidateInfoRequestModel.phoneNumber)) {
            throw BaseException(BaseResponseCode.DUPLICATE_PHONE_NUMBER)
        }

        val actualToken = token.substring("Bearer ".length)
        val email = jwtTokenUtil.extractUsername(actualToken)
        val candidateInfoRequestModelWithCrewId = candidateInfoRequestModel.apply {
            this.crew = email
        }

        val candidateDto = candidateMapper.toDto(candidateInfoRequestModelWithCrewId)

        return BaseResponseModel(HttpStatus.OK.value(), candidateMapper.toModel(candidateDetailService.createCandidate(candidateDto)).id)
    }

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(summary = "소개팅 남녀 사진 업로드", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun uploadFiles(
        @RequestPart("files") files: List<MultipartFile>): BaseResponseModel<List<String>> {
        return BaseResponseModel(HttpStatus.OK.value(), fileStorageService.uploadFiles(files))
    }

}
