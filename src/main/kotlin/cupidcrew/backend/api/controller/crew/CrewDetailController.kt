package cupidcrew.backend.api.controller.crew

import cupidcrew.backend.api.model.BaseResponseModel
import cupidcrew.backend.api.model.candidate.CandidateInfoResponseModel
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "[Crew-detail]", description = "소개팅 주선자 관련 기능 api")
@RestController
@RequestMapping("/crew")
class CrewDetailController() {

    @Operation(summary = "다른 소개팅 주선자에게 연락한 기록 남기기", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/send")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun remainSendingHistory(

    ): BaseResponseModel<List<CandidateInfoResponseModel>> {
        val actualToken = token.substring("Bearer ".length)
        val crewEmail = jwtTokenUtil.extractUsername(actualToken)
        val crew = crewService.findCrewByEmail(crewEmail)

        val candidatesDto = candidateDetailService.retrieveMyCandidates(crew)

        return BaseResponseModel(HttpStatus.OK.value(), candidatesDto.map { candidateMapper.toModel(it) })
    }




}