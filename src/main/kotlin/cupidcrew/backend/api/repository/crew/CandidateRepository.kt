package cupidcrew.backend.api.repository.crew

import cupidcrew.backend.api.dao.candidate.CandidateEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CandidateRepository : JpaRepository<CandidateEntity, Long> {
    fun existsByPhoneNumber(phoneNumber: String): Boolean
}
