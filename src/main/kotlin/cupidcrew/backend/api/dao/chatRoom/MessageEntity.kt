package cupidcrew.backend.api.dao.chatRoom

import ChatRoomEntity
import javax.persistence.*
import java.util.Date

@Entity
@Table(name = "messages")
data class MessageEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val senderId: Long,
    val content: String,
    val creationDate: Date = Date(),
    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    val chatRoom: ChatRoomEntity
)