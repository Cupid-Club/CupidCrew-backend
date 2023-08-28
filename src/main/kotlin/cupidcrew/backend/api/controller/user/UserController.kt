package cupidcrew.backend.api.controller.user

import cupidcrew.api.backend.exception.BaseException
import cupidcrew.api.backend.exception.BaseResponseCode
import cupidcrew.backend.api.dao.user.UserEntity
import cupidcrew.backend.api.model.user.UserLoginRequestModel
import cupidcrew.backend.api.model.user.UserLoginResponseModel
import cupidcrew.backend.api.model.user.UserSignupRequestModel
import cupidcrew.backend.api.model.user.UserSignupResponseModel
import cupidcrew.backend.api.service.user.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

// @PreAuthorize("hasAnyAuthority('ROLE_DASHBOARD')")
@Tag(name = "[User]", description = "회원가입 및 로그인")
@RestController
class UserController(private val userService: UserService, private val passwordEncoder: PasswordEncoder) {
    @Operation(summary = "회원가입", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/signup")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun signup(@RequestBody userSignupRequestModel: UserSignupRequestModel): ResponseEntity<UserSignupResponseModel> {
        if (userService.existsUser(userSignupRequestModel.email)) {
            throw BaseException(BaseResponseCode.DUPLICATE_EMAIL)
        }
        userSignupRequestModel.password = passwordEncoder.encode(userSignupRequestModel.password)
        return ResponseEntity.ok(userService.createUser(userSignupRequestModel))
    }

    @Operation(summary = "로그인", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/login")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun login(@RequestBody userLoginReqestModel: UserLoginRequestModel): ResponseEntity<UserLoginResponseModel> {
        val user: UserEntity = userService.findUser(userLoginReqestModel.email)

        if (!passwordEncoder.matches(userLoginReqestModel.password, user.m_password)) {
            throw BaseException(BaseResponseCode.INVALID_PASSWORD)
        }

        return ResponseEntity.ok(userService.login(userLoginReqestModel))
    }
}
