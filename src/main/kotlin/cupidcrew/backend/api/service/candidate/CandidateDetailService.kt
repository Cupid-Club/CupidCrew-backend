package cupidcrew.backend.api.service.candidate

import cupidcrew.backend.api.dao.candidate.CandidateEntity
import cupidcrew.backend.api.dao.crew.CrewEntity
import cupidcrew.backend.api.dao.crew.QCrewEntity.crewEntity
import cupidcrew.backend.api.dto.candidate.CandidateInfoRequestDto
import cupidcrew.backend.api.dto.crew.CrewFirebaseTokenResponseDto
import cupidcrew.backend.api.dto.crew.CrewSignupRequestDto
import cupidcrew.backend.api.mapper.candidate.CandidateMapper
import cupidcrew.backend.api.mapper.crew.CrewMapper
import cupidcrew.backend.api.repository.candidate.CandidateRepository
// import cupidcrew.backend.api.repository.candidate.CandidateRepositoryCustomImpl
import org.springframework.stereotype.Service
import java.util.*

@Service
class CandidateDetailService(
    private val candidateRepository: CandidateRepository,
    private val candidateMapper: CandidateMapper,
    private val crewMapper: CrewMapper,
) {
    fun retrieveMyCandidates(crew: CrewEntity): List<CandidateInfoRequestDto> {
        val candidates = candidateRepository.findByCrew(crew)
        return candidates.map { candidateMapper.toDto(it) }
    }

    fun createCandidate(candidateDto: CandidateInfoRequestDto): CandidateInfoRequestDto {
        val candidateEntity = candidateMapper.toEntity(candidateDto)
        return candidateMapper.toDto(candidateRepository.save(candidateEntity))
    }

    fun reviseCandidate(candidateDto: CandidateInfoRequestDto): CandidateInfoRequestDto {
        val candidateEntity = candidateMapper.toEntity(candidateDto)
        val updatedCandidate = candidateRepository.save(candidateEntity)

        return candidateMapper.toDto(updatedCandidate)
    }

    fun deleteCandidate(candidateId: Long) {
        candidateRepository.deleteById(candidateId)
    }

    fun increasePopularity(candidateId: Long) {
        candidateRepository.increaseField(candidateId, "popularity")
    }

    fun increaseOpportunity(candidateId: Long) {
        candidateRepository.increaseField(candidateId, "opportunity")
    }

    fun getCrewIdBySelectedCandidateId(candidateId: Long): CrewFirebaseTokenResponseDto? {
        val candidate = candidateRepository.findById(candidateId).orElse(null)
        return candidate?.crew?.let { crewMapper.toDtoFirebaseToken(it) }
    }

}
