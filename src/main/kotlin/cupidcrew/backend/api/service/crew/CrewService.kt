package cupidcrew.backend.api.service.crew

import cupidcrew.api.backend.exception.BaseException
import cupidcrew.api.backend.exception.BaseResponseCode
import cupidcrew.backend.api.dao.crew.CrewEntity
import cupidcrew.backend.api.model.crew.CrewLoginRequestModel
import cupidcrew.backend.api.model.crew.CrewSignupRequestModel
import cupidcrew.backend.api.repository.crew.CrewRepository
import cupidcrew.backend.api.security.JwtTokenProvider
import org.springframework.stereotype.Service

@Service
class CrewService(private val crewRepository: CrewRepository, private val jwtTokenProvider: JwtTokenProvider) {

    fun findCrew(email: String): CrewEntity {
        return crewRepository.findByEmail(email) ?: throw BaseException(BaseResponseCode.CREW_NOT_FOUND)
    }

    fun existsCrew(email: String): Boolean {
        return crewRepository.existsByEmail(email)
    }

    fun createCrew(crewSignupRequestModel: CrewSignupRequestModel): CrewEntity {
        val crew = CrewEntity(crewSignupRequestModel.name, crewSignupRequestModel.email, crewSignupRequestModel.password)
        crewRepository.save(crew)

        return crew
    }

    fun login(crewLoginRequsetModel: CrewLoginRequestModel): String {
        return jwtTokenProvider.createToken(crewLoginRequsetModel.email)
    }
}
