package cupidcrew.backend.api.service.admin

import cupidcrew.backend.api.repository.crew.CrewRepository
import org.springframework.stereotype.Service

@Service
class AdminService(
        private val crewRepository: CrewRepository,
) {

    fun changeApprovedStatus(crewId: Long) {
        crewRepository.changeBooleanStatus(crewId, "approved", true)
    }

}