package cupidcrew.backend.api.repository.chatRoom

import ChatRoomEntity
import cupidcrew.backend.api.dao.crew.CrewEntity
import org.springframework.data.jpa.repository.JpaRepository
interface ChatRoomRepository : JpaRepository<ChatRoomEntity, Long> {

    fun findByChatRoomId(chatRoomId: Long) : ChatRoomEntity?

}