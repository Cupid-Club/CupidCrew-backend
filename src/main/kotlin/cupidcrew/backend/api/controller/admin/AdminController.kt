package cupidcrew.backend.api.controller.admin

import cupidcrew.backend.api.exception.BaseException
import cupidcrew.backend.api.exception.BaseResponseCode
import cupidcrew.backend.api.model.BaseResponseModel
import cupidcrew.backend.api.model.authentication.AuthenticationRequestModel
import cupidcrew.backend.api.security.JwtTokenUtil
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*

@RestController
class AdminController(
        private val authenticationManager: AuthenticationManager,
        private val userDetailsService: UserDetailsService,
        private val jwtTokenUtil: JwtTokenUtil,
) {

    @GetMapping("/")
    fun home() : String {
        return "<h1>welcome</h1>"
    }

    @RequestMapping(value = ["/authenticate"], method = [RequestMethod.POST])
    fun authenticate(@RequestBody authenticationRequestModel: AuthenticationRequestModel): BaseResponseModel<String> {
        try {
            authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(authenticationRequestModel.username, authenticationRequestModel.password)
            )
        } catch (_: BadCredentialsException) {
            throw BaseException(BaseResponseCode.BAD_CREDENTIALS)
        }

        val userDetails = userDetailsService.loadUserByUsername(authenticationRequestModel.username)

        return BaseResponseModel(HttpStatus.OK.value(), jwtTokenUtil.generateToken(userDetails))

    }

}