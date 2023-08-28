package cupidcrew.backend.api.mapper.candidate

import cupidcrew.backend.api.dao.candidate.CandidateEntity
import cupidcrew.backend.api.dto.candidate.CandidateInfoRequestDto
import cupidcrew.backend.api.model.candidate.CandidateInfoRequestModel
import cupidcrew.backend.api.model.candidate.CandidateInfoResponseModel
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.springframework.stereotype.Component

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
abstract class CandidateMapper {

    abstract fun toDto(model: CandidateInfoRequestModel): CandidateInfoRequestDto

    abstract fun toEntity(dto: CandidateInfoRequestDto): CandidateEntity

    abstract fun toModel(entity: CandidateEntity): CandidateInfoResponseModel
}
