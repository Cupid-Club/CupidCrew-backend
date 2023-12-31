package cupidcrew.backend.api.service.crew

import cupidcrew.backend.api.dao.crew.CrewEntity
import cupidcrew.backend.api.dto.crew.CrewFindIdResponseDto
import cupidcrew.backend.api.dto.crew.CrewLoginRequestDto
import cupidcrew.backend.api.dto.crew.CrewSignupRequestDto
import cupidcrew.backend.api.exception.BaseException
import cupidcrew.backend.api.exception.BaseResponseCode
import cupidcrew.backend.api.mapper.crew.CrewMapper
import cupidcrew.backend.api.repository.crew.CrewRepository
import cupidcrew.backend.api.security.JwtTokenUtil
import org.springframework.stereotype.Service

@Service
class CrewService(
    private val crewRepository: CrewRepository,
    private val crewMapper: CrewMapper,
) {

    fun findCrewByEmail(email: String): CrewEntity {
        return crewRepository.findByEmail(email) ?: throw BaseException(BaseResponseCode.CREW_NOT_FOUND)
    }

    fun findCrewByNameAndMutualFriend(name: String, mutualFriend: String): List<CrewFindIdResponseDto> {
        val crewEntityList = crewRepository.findByNameAndMutualFriend(name, mutualFriend)
        if (crewEntityList.isEmpty()) throw BaseException(BaseResponseCode.CREW_NOT_FOUND)
        return crewEntityList.map { crewMapper.toDtoFindId(it) }
    }


    fun findCrewByNameAndEmail(name: String, email: String): CrewEntity {
        return crewRepository.findByNameAndEmail(name, email) ?: throw BaseException(BaseResponseCode.CREW_NOT_FOUND)
    }

    fun existsCrew(email: String): Boolean {
        return crewRepository.existsByEmail(email)
    }

    fun createCrew(crewDto: CrewSignupRequestDto): CrewSignupRequestDto {
        val crewEntity = crewMapper.toEntity(crewDto)
        crewRepository.save(crewEntity)

        return crewMapper.toDto(crewEntity)
    }

    fun resetPassword(crewId: Long, encodedNewPassword: String) {
        return crewRepository.resetPassword(crewId, encodedNewPassword)
    }

}
