package cupidcrew.backend.api.dto.chatRoom

import java.util.Date

data class MessageRequestDto(
    val id: Long?,
    val chatRoomId: Long,
    val senderId: Long,
    val content: String,
    val creationDate: Date
)