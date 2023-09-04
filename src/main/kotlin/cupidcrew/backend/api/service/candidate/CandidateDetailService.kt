package cupidcrew.backend.api.service.candidate

import cupidcrew.backend.api.dao.crew.CrewEntity
import cupidcrew.backend.api.dao.crew.QCrewEntity.crewEntity
import cupidcrew.backend.api.dto.candidate.CandidateInfoRequestDto
import cupidcrew.backend.api.mapper.candidate.CandidateMapper
import cupidcrew.backend.api.repository.candidate.CandidateRepository
// import cupidcrew.backend.api.repository.candidate.CandidateRepositoryCustomImpl
import org.springframework.stereotype.Service

@Service
class CandidateDetailService(
    private val candidateRepository: CandidateRepository,
    private val candidateMapper: CandidateMapper,
) {
    fun retrieveMyCandidates(crew: CrewEntity): List<CandidateInfoRequestDto> {
        val candidates = candidateRepository.findByCrew(crew)
        return candidates.map { candidateMapper.toDto(it) }
    }

    fun createCandidate(candidateDto: CandidateInfoRequestDto): CandidateInfoRequestDto {
        val candidateEntity = candidateMapper.toEntity(candidateDto)
        candidateRepository.save(candidateEntity)

        return candidateMapper.toDto(candidateEntity)
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
}
