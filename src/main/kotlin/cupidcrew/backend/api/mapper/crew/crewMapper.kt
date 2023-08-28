package cupidcrew.backend.api.mapper.crew

import cupidcrew.backend.api.dao.candidate.CandidateEntity
import cupidcrew.backend.api.dao.crew.CrewEntity
import cupidcrew.backend.api.dto.candidate.CandidateInfoRequestDto
import cupidcrew.backend.api.dto.crew.CrewLoginRequestDto
import cupidcrew.backend.api.dto.crew.CrewSignupRequestDto
import cupidcrew.backend.api.model.candidate.CandidateInfoRequestModel
import cupidcrew.backend.api.model.candidate.CandidateInfoResponseModel
import cupidcrew.backend.api.model.crew.CrewLoginRequestModel
import cupidcrew.backend.api.model.crew.CrewSignupRequestModel
import cupidcrew.backend.api.model.crew.CrewSignupResponseModel
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.springframework.stereotype.Component

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
abstract class CrewMapper {

    //signup
    abstract fun toDto(model: CrewSignupRequestModel): CrewSignupRequestDto
    abstract fun toEntity(dto: CrewSignupRequestDto): CrewEntity
    abstract fun toDto(entity: CrewEntity): CrewSignupRequestDto
    abstract fun toModel(dto: CrewSignupRequestDto): CrewSignupResponseModel

    // login
    abstract fun toDto(model: CrewLoginRequestModel): CrewLoginRequestDto

}