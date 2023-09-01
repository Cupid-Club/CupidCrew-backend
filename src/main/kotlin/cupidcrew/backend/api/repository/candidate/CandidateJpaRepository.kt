package cupidcrew.backend.api.repository.candidate

import cupidcrew.backend.api.dao.candidate.CandidateEntity
import cupidcrew.backend.api.dao.crew.CrewEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CandidateJpaRepository : JpaRepository<CandidateEntity, Long> {

    fun findByStatus(status: String): List<CandidateEntity>

    fun existsByPhoneNumber(phoneNumber: String): Boolean

    fun findByCrew(crew: CrewEntity): List<CandidateEntity>
}
