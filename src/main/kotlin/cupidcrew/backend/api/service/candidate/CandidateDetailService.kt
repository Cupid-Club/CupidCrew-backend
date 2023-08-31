package cupidcrew.backend.api.service.candidate

import cupidcrew.backend.api.dto.candidate.CandidateInfoRequestDto
import cupidcrew.backend.api.mapper.candidate.CandidateMapper
import cupidcrew.backend.api.repository.candidate.CandidateRepository
//import cupidcrew.backend.api.repository.candidate.CandidateRepositoryCustomImpl
import org.springframework.stereotype.Service

@Service
class CandidateDetailService(
    private val candidateRepository: CandidateRepository,
    private val candidateMapper: CandidateMapper,
//    private val candidateRepositoryImplCustom: CandidateRepositoryCustomImpl,
) {
    fun retrieveMyCandidates(crewId: Long): List<CandidateInfoRequestDto> {
        val candidates = candidateRepository.findById(crewId).orElse(null)
        return candidates?.let { listOf(candidateMapper.toDto(it)) } ?: emptyList()
    }

    fun createCandidate(candidateDto: CandidateInfoRequestDto): CandidateInfoRequestDto {
        val candidateEntity = candidateMapper.toEntity(candidateDto)
        candidateRepository.save(candidateEntity)

        return candidateMapper.toDto(candidateEntity)
    }

    fun deleteCandidate(candidateId: Long) {
        candidateRepository.deleteById(candidateId)
    }

//    fun increasePopularity(candidateId: Long) {
//        candidateRepositoryImplCustom.increaseField(candidateId, "popularity")
//    }
//
//    fun increaseOpportunity(candidateId: Long) {
//        candidateRepositoryImplCustom.increaseField(candidateId, "opportunity")
//    }
}