package cupidcrew.backend.api.controller.crew

import cupidcrew.api.backend.exception.BaseException
import cupidcrew.api.backend.exception.BaseResponseCode
import cupidcrew.backend.api.dao.crew.CrewEntity
import cupidcrew.backend.api.mapper.crew.CrewMapper
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
import org.springframework.http.ResponseEntity
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
    fun signup(@RequestBody crewSignupRequestModel: CrewSignupRequestModel): ResponseEntity<CrewSignupResponseModel> {
        if (crewService.existsCrew(crewSignupRequestModel.email)) {
            throw BaseException(BaseResponseCode.DUPLICATE_EMAIL)
        }
//        crewSignupRequestModel.password = passwordEncoder.encode(crewSignupRequestModel.password)

        val crewDto = crewMapper.toDto(crewSignupRequestModel).apply {
            this.m_password = passwordEncoder.encode(crewSignupRequestModel.password)
        }

        return ResponseEntity.ok(crewMapper.toModel(crewService.createCrew(crewDto)))
    }

    @Operation(summary = "로그인", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/login")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun login(@RequestBody crewLoginReqestModel: CrewLoginRequestModel): ResponseEntity<CrewLoginResponseModel> {
        val crew: CrewEntity = crewService.findCrew(crewLoginReqestModel.email)

        if (!passwordEncoder.matches(crewLoginReqestModel.password, crew.m_password)) {
            throw BaseException(BaseResponseCode.INVALID_PASSWORD)
        }

        val crewDto = crewMapper.toDto(crewLoginReqestModel)

        val token = crewService.login(crewDto)

        val responseModel = CrewLoginResponseModel(
            httpStatus = HttpStatus.OK,
            token = token,
        )

        return ResponseEntity.ok(responseModel)
    }
}
