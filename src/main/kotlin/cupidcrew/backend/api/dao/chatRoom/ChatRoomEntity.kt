import cupidcrew.backend.api.dao.chatRoom.MessageEntity
import javax.persistence.*
import java.util.Date

@Entity
@Table(name = "chat_rooms")
data class ChatRoomEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val senderId: Long,
    val receiverId: Long,
    val creationDate: Date = Date(),
    @OneToMany(mappedBy = "chatRoomEntity", cascade = [CascadeType.ALL])
    val messages: MutableList<MessageEntity> = mutableListOf()
)
