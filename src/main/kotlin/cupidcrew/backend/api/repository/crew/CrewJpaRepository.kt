package cupidcrew.backend.api.repository.crew

import cupidcrew.backend.api.dao.crew.CrewEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CrewJpaRepository : JpaRepository<CrewEntity, Long> {
    fun findByEmail(email: String): CrewEntity?

    fun existsByEmail(email: String): Boolean

    fun findByApprovedIsFalse(): List<CrewEntity>

    fun findByNameAndMutualFriend(name: String, mutualFriend: String): List<CrewEntity>

    fun findByNameAndEmail(name: String, email: String): CrewEntity?

}