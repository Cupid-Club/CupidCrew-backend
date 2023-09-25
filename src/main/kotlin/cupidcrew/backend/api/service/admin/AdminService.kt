package cupidcrew.backend.api.service.admin

import cupidcrew.backend.api.dao.crew.CrewEntity
import cupidcrew.backend.api.dto.candidate.CandidateInfoRequestDto
import cupidcrew.backend.api.dto.crew.CrewSignupRequestDto
import cupidcrew.backend.api.dto.crew.CrewSignupResponseDto
import cupidcrew.backend.api.mapper.crew.CrewMapper
import cupidcrew.backend.api.repository.crew.CrewRepository
import org.springframework.stereotype.Service

@Service
class AdminService(
        private val crewRepository: CrewRepository,
        private val crewMapper: CrewMapper,
) {

    fun changeApprovedStatus(crewId: Long) {
        crewRepository.changeBooleanStatus(crewId, "approved", true)
    }

    fun retrieveNotApprovedCrew() : List<CrewSignupRequestDto> {
        val crewsEntity = crewRepository.findByApprovedIsFalse()
        return crewsEntity.map { crewMapper.toDto(it)}
    }

}
