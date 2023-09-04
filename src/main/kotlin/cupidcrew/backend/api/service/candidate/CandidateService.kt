package cupidcrew.backend.api.service.candidate

import cupidcrew.backend.api.dto.candidate.CandidateInfoRequestDto
import cupidcrew.backend.api.mapper.candidate.CandidateMapper
import cupidcrew.backend.api.repository.candidate.CandidateRepository
import org.springframework.stereotype.Service

@Service
class CandidateService(
    private val candidateRepository: CandidateRepository,
    private val candidateMapper: CandidateMapper,
) {
    fun retrieveAllCandidates(): List<CandidateInfoRequestDto> {
        val candidates = candidateRepository.findAll()
        return candidates.map { candidateMapper.toDto(it) }
    }

    fun retrieveSingleCandidates(): List<CandidateInfoRequestDto> {
        val candidates = candidateRepository.findByStatus("single")
        return candidates.map { candidateMapper.toDto(it) }
    }

    fun existsCandidateByPhoneNumber(phoneNumber: String): Boolean {
        return candidateRepository.existsByPhoneNumber(phoneNumber)
    }

    fun existsCandidateById(candidateId: Long): Boolean {
        return candidateRepository.existsById(candidateId)
    }
}
