package cupidcrew.backend.api.controller.admin

import cupidcrew.backend.api.dao.crew.CrewEntity
import cupidcrew.backend.api.mapper.crew.CrewMapper
import cupidcrew.backend.api.model.BaseResponseModel
import cupidcrew.backend.api.model.crew.CrewEmailRequestModel
import cupidcrew.backend.api.model.crew.CrewSignupResponseModel
import cupidcrew.backend.api.model.notification.NotificationReceiverModel
import cupidcrew.backend.api.service.admin.AdminService
import cupidcrew.backend.api.service.crew.CrewService
import cupidcrew.backend.api.service.notification.NotificationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Tag(name = "[ADMIN]", description = "관리자용 api들")
@RestController
@RequestMapping("/admin")
class AdminController(
        private val crewService: CrewService,
        private val adminService: AdminService,
        private val notificationService: NotificationService,
        private val crewMapper: CrewMapper,

        ) {
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "회원가입을 위한 승인 절차", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/changeApprovedStatus")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun changeApprovedStatus(
            @RequestBody crewEmailRequestModel: CrewEmailRequestModel,
    ) : BaseResponseModel<String> {
        val crew: CrewEntity = crewService.findCrewByEmail(crewEmailRequestModel.email)

        adminService.changeApprovedStatus(crew.crewid!!)

        return BaseResponseModel(HttpStatus.OK.value(), "This person is approved as crew")
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "회원가입 승인 기다리는 사람들 리스트 보기", security = [SecurityRequirement(name = "bearerAuth")])
    @GetMapping("/ApprovedWaiting")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK")])
    fun retrieveApprovedWaitingList() : BaseResponseModel<List<CrewSignupResponseModel>> {

        val crewsDto = adminService.retrieveNotApprovedCrew()

        return BaseResponseModel(HttpStatus.OK.value(), crewsDto.map { crewMapper.toModel(it) })
    }

}