package cupidcrew.backend.api.repository.matching

import cupidcrew.backend.api.dao.matching.MatchingEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MatchingJpaRepository : JpaRepository<MatchingEntity, Long> {
}