package cupidcrew.backend.api.repository.chatRoom

import cupidcrew.backend.api.dao.chatRoom.MessageEntity
import org.springframework.data.jpa.repository.JpaRepository
interface MessageRepository : JpaRepository<MessageEntity, Long>