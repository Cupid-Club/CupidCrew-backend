package cupidcrew.backend.api.service.candidate

import cupidcrew.backend.api.dao.crew.CrewEntity
import cupidcrew.backend.api.dto.candidate.CandidateInfoRequestDto
import cupidcrew.backend.api.exception.BaseException
import cupidcrew.backend.api.exception.BaseResponseCode
import cupidcrew.backend.api.mapper.candidate.CandidateMapper
import cupidcrew.backend.api.repository.candidate.CandidateRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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


    fun retrieveMyCandidates(crew: CrewEntity): List<CandidateInfoRequestDto> {
        val candidates = candidateRepository.findByCrew(crew)
        return candidates.map { candidateMapper.toDto(it) }
    }

    @Transactional
    fun createCandidate(candidateDto: CandidateInfoRequestDto): CandidateInfoRequestDto {
        val candidateEntity = candidateMapper.toEntity(candidateDto)
        return candidateMapper.toDto(candidateRepository.save(candidateEntity))
    }

    @Transactional
    fun updateCandidate(candidateId: Long, candidateDto: CandidateInfoRequestDto): CandidateInfoRequestDto {
        val candidateEntity = candidateRepository.findById(candidateId)
            .orElseThrow { throw BaseException(BaseResponseCode.CANDIDATE_NOT_FOUND) }

        candidateEntity.apply {
            status = candidateDto.status
            age = candidateDto.age
            height = candidateDto.height
            weight = candidateDto.weight
            address = candidateDto.address
            job = candidateDto.job
            mbti = candidateDto.mbti
            personality = candidateDto.personality
            idealType = candidateDto.idealType
            imagesUrl = candidateDto.imagesUrl
            popularity = candidateDto.popularity
            opportunity = candidateDto.opportunity
        }
        val updatedCandidate = candidateRepository.save(candidateEntity)

        return candidateMapper.toDto(updatedCandidate)
    }

    @Transactional
    fun deleteCandidate(candidateId: Long) {
        val candidateEntity = candidateRepository.findById(candidateId)
            .orElseThrow { throw BaseException(BaseResponseCode.CANDIDATE_NOT_FOUND) }

        candidateRepository.delete(candidateEntity)
    }
}
