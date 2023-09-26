package cupidcrew.backend.api.model.chatRoom

import org.springframework.web.bind.annotation.RequestParam

data class ChatRoomRequestModel (
    val senderId: Long,
    val receiverId: Long
)