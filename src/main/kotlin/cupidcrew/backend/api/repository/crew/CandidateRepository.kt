package cupidcrew.backend.api.repository.crew

import cupidcrew.backend.api.dao.candidate.CandidateEntity
import cupidcrew.backend.api.dao.crew.CrewEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CandidateRepository : JpaRepository<CandidateEntity, Long> {
    fun existsByPhoneNumber(phoneNumber: String): Boolean
    fun findByCrew(crew: CrewEntity): List<CandidateEntity>?
}
