package cupidcrew.backend.api.model.chatRoom

data class MessageRequestModel(
    val chatRoomId: Long,
    val senderId: Long,
    val content: String
)
