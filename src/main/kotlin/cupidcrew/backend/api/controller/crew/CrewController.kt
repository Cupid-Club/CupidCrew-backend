package cupidcrew.backend.api.controller.crew

import cupidcrew.backend.api.dao.crew.CrewEntity
import cupidcrew.backend.api.dto.crew.CrewFindIdResponseDto
import cupidcrew.backend.api.exception.BaseException
import cupidcrew.backend.api.exception.BaseResponseCode
import cupidcrew.backend.api.mapper.crew.CrewMapper
import cupidcrew.backend.api.model.BaseResponseModel
import cupidcrew.backend.api.model.crew.*
import cupidcrew.backend.api.security.JwtTokenUtil
import cupidcrew.backend.api.service.admin.BlacklistTokenService
import cupidcrew.backend.api.service.admin.ProjectUserDetailsService
import cupidcrew.backend.api.service.crew.CrewService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@Tag(name = "[Crew]", description = "회원가입 및 로그인")
@RestController
@RequestMapping("/crew")
class CrewController(
    private val jwtTokenUtil: JwtTokenUtil,
    private val crewService: CrewService,
    private val passwordEncoder: PasswordEncoder,
    private val crewMapper: CrewMapper,
    private val userDetailsService: ProjectUserDetailsService,
    private val blacklistTokenService: BlacklistTokenService
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
            this.approved = crewSignupRequestModel.email == "admin@naver.com"
            this.role = "ROLE_CREW"
        }

        // 우선 저장함 aprroved = false 인 상태
        crewService.createCrew(crewDto)

        return BaseResponseModel(HttpStatus.OK.value(), "승인 심사 진행하겠습니다.")
    }

    @Operation(summary = "로그인", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/login")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun login(@RequestBody crewLoginReqestModel: CrewLoginRequestModel): BaseResponseModel<String> {
        val crew: CrewEntity = crewService.findCrewByEmail(crewLoginReqestModel.email)

        if (!crew.approved) {
            throw BaseException(BaseResponseCode.NOT_YET_APPROVED_AS_CREW)
        }

        if (!passwordEncoder.matches(crewLoginReqestModel.password, crew.m_password)) {
            throw BaseException(BaseResponseCode.INVALID_PASSWORD)
        }

        val userDetails = userDetailsService.loadUserByUsername(crew.email)

        return BaseResponseModel(HttpStatus.OK.value(), jwtTokenUtil.generateToken(userDetails))
    }

    @Operation(summary = "로그아웃", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/logout")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun logout(@RequestHeader("Authorization") token: String): BaseResponseModel<String> {
        blacklistTokenService.blacklist(token.substring(7))
        return BaseResponseModel(HttpStatus.OK.value(), "Successfully logged out")
    }


    @Operation(summary = "아이디(email) 찾기", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/find/id")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun findId(@RequestBody crewFindIdRequestModel: CrewFindIdRequestModel): BaseResponseModel<List<CrewFindIdResponseDto>> {
        val crewDtoList = crewService.findCrewByNameAndMutualFriend(crewFindIdRequestModel.name, crewFindIdRequestModel.mutualFriend)
        return BaseResponseModel(HttpStatus.OK.value(), crewDtoList)
    }

    @Operation(summary = "패스워드 초기화", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/find/password/reset")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun resetPassword(@RequestBody crewResetPasswordRequestModel: CrewResetPasswordRequestModel): BaseResponseModel<String> {
        val crewEntity = crewService.findCrewByNameAndEmail(crewResetPasswordRequestModel.name, crewResetPasswordRequestModel.email)

        val encodedNewPassword = passwordEncoder.encode(crewResetPasswordRequestModel.newPassword)

        crewService.resetPassword(crewEntity.crewid!!, encodedNewPassword)

        return BaseResponseModel(HttpStatus.OK.value(), "Successfully password is reset!")
    }

}
