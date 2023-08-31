package cupidcrew.backend.api.repository.candidate

import cupidcrew.backend.api.dao.candidate.CandidateEntity
import org.springframework.data.jpa.repository.JpaRepository

//interface CandidateRepository : JpaRepository<CandidateEntity, Long>, CandidateRepositoryCustom {
interface CandidateRepository : JpaRepository<CandidateEntity, Long> {

    fun findByStatus(status: String): List<CandidateEntity>

    fun existsByPhoneNumber(phoneNumber: String): Boolean
}
