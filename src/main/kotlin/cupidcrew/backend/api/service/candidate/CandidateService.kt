package cupidcrew.backend.api.service.candidate

import cupidcrew.api.backend.exception.BaseException
import cupidcrew.api.backend.exception.BaseResponseCode
import cupidcrew.backend.api.dao.candidate.CandidateEntity
import cupidcrew.backend.api.dao.crew.CrewEntity
import cupidcrew.backend.api.dto.candidate.CandidateInfoRequestDto
import cupidcrew.backend.api.mapper.candidate.CandidateMapper
import cupidcrew.backend.api.model.candidate.CandidateInfoRequestModel
import cupidcrew.backend.api.repository.crew.CandidateRepository
import org.springframework.stereotype.Service

@Service
class CandidateService(
    private val candidateRepository: CandidateRepository,
    private val candidateMapper: CandidateMapper,
) {

    fun existsCandidate(phoneNumber: String): Boolean {
        return candidateRepository.existsByPhoneNumber(phoneNumber)
    }

    fun createCandidate(candidateDto: CandidateInfoRequestDto): CandidateEntity {
        val candidateEntity = candidateMapper.toEntity(candidateDto)
        candidateRepository.save(candidateEntity)

        return candidateEntity
    }
}
