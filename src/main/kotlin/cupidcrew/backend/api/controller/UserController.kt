package cupidcrew.backend.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "[User]", description = "회원가입 및 로그인")
class UserController {

    @Operation(summary = "회원가입 및 로그인")
    @PostMapping("/signup")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun register(@RequestBody name: String): ResponseEntity<String> {

        return ResponseEntity.ok("success")
    }

}