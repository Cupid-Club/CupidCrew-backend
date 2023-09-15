package cupidcrew.backend.api.controller.crew

import cupidcrew.backend.api.dao.crew.CrewEntity
import cupidcrew.backend.api.exception.BaseException
import cupidcrew.backend.api.exception.BaseResponseCode
import cupidcrew.backend.api.mapper.crew.CrewMapper
import cupidcrew.backend.api.model.BaseResponseModel
import cupidcrew.backend.api.model.crew.CrewLoginRequestModel
import cupidcrew.backend.api.model.crew.CrewLoginResponseModel
import cupidcrew.backend.api.model.crew.CrewSignupRequestModel
import cupidcrew.backend.api.model.crew.CrewSignupResponseModel
import cupidcrew.backend.api.service.crew.CrewService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// @PreAuthorize("hasAnyAuthority('ROLE_DASHBOARD')")
@Tag(name = "[Crew]", description = "회원가입 및 로그인")
@RestController
@RequestMapping("/crew")
class CrewController(
    private val crewService: CrewService,
    private val passwordEncoder: PasswordEncoder,
    private val crewMapper: CrewMapper,
) {
    @Operation(summary = "회원가입", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/signup")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun signup(@RequestBody crewSignupRequestModel: CrewSignupRequestModel): BaseResponseModel<String> {
        if (crewService.existsCrew(crewSignupRequestModel.email)) {
            throw BaseException(BaseResponseCode.DUPLICATE_EMAIL)
        }

        val crewDto = crewMapper.toDto(crewSignupRequestModel).apply {
            this.m_password = passwordEncoder.encode(crewSignupRequestModel.password)
        }

        // 우선 저장함 aprroved = false 인 상태
        crewService.createCrew(crewDto)

        return BaseResponseModel(HttpStatus.OK.value(), "승인 심사 진행하겠습니다.")
    }

    @Operation(summary = "로그인", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/login")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun login(@RequestBody crewLoginReqestModel: CrewLoginRequestModel): BaseResponseModel<CrewLoginResponseModel> {
        val crew: CrewEntity = crewService.findCrew(crewLoginReqestModel.email)

        if (!crew.approved) {
            throw BaseException(BaseResponseCode.NOT_YET_APPROVED_AS_CREW)
        }

        if (!passwordEncoder.matches(crewLoginReqestModel.password, crew.m_password)) {
            throw BaseException(BaseResponseCode.INVALID_PASSWORD)
        }

        val crewDto = crewMapper.toDto(crewLoginReqestModel)

        return BaseResponseModel(HttpStatus.OK.value(), CrewLoginResponseModel(crewService.login(crewDto)))
    }
}
