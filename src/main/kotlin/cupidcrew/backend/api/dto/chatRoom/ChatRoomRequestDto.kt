package cupidcrew.backend.api.dto.chatRoom

import java.util.Date

data class ChatRoomRequestDto(
    val id: Long?,
    val senderId: Long,
    val receiverId: Long,
    val creationDate: Date,
    val messages: List<MessageRequestDto>
)