package cupidcrew.backend.api.mapper.candidate

import cupidcrew.backend.api.dao.candidate.CandidateEntity
import cupidcrew.backend.api.dao.crew.CrewEntity
import cupidcrew.backend.api.dto.candidate.CandidateInfoRequestDto
import cupidcrew.backend.api.model.candidate.CandidateInfoRequestModel
import cupidcrew.backend.api.model.candidate.CandidateInfoResponseModel
import cupidcrew.backend.api.repository.crew.CrewRepository
import org.mapstruct.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
abstract class CandidateMapper {
    @Autowired
    lateinit var crewRepository: CrewRepository
    abstract fun toDto(model: CandidateInfoRequestModel): CandidateInfoRequestDto

    @Mapping(source = "crew", target = "crew", qualifiedByName = ["mapToCrewEntity"])
    abstract fun toEntity(dto: CandidateInfoRequestDto): CandidateEntity

    @Mapping(source = "crew.email", target = "crew")
    abstract fun toDto(entity: CandidateEntity): CandidateInfoRequestDto

    @Named("mapToCrewEntity")
    fun mapToCrewEntity(crewEmail: String?): CrewEntity? {
        return crewEmail?.let {
            crewRepository.findByEmail(it)
        }
    }

    abstract fun toModel(dto: CandidateInfoRequestDto): CandidateInfoResponseModel
}


