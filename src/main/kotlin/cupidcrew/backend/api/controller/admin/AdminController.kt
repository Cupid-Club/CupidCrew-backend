package cupidcrew.backend.api.controller.admin

import cupidcrew.backend.api.exception.BaseException
import cupidcrew.backend.api.exception.BaseResponseCode
import cupidcrew.backend.api.mapper.candidate.CandidateMapper
import cupidcrew.backend.api.model.BaseResponseModel
import cupidcrew.backend.api.model.authentication.AuthenticationRequestModel
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
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*

@Tag(name = "[ADMIN]", description = "관리자용 api들")
@RestController
@RequestMapping("/admin")
class AdminController(
        private val crewService: CrewService,
        private val candidateService: CandidateService,
        private val candidateDetailService: CandidateDetailService,
        private val candidateMapper: CandidateMapper,
        private val jwtTokenUtil: JwtTokenUtil,
        private val fileStorageService: FileStorageService,

        ) {
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "회원가입을 위한 승인 절차", security = [SecurityRequirement(name = "bearerAuth")])
    @GetMapping("/")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun retrieveAllCandidates(
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
}