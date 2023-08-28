package cupidcrew.backend.api.repository.crew

import cupidcrew.backend.api.dao.crew.CrewEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CrewRepository : JpaRepository<CrewEntity, Long> {

    fun findByEmail(email: String): CrewEntity?
    fun existsByEmail(email: String): Boolean
}
