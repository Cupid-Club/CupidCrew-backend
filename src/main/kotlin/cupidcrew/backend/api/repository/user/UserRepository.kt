package cupidcrew.backend.api.repository.user

import cupidcrew.backend.api.dao.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {

    fun findByEmail(email: String): UserEntity?
    fun existsByEmail(email: String): Boolean
}
