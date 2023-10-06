package cupidcrew.backend.api.model.chatting

data class ChatRoom(
    val name: String,
    val participants: MutableList<String> = mutableListOf(),
    val messages: MutableList<ChatMessage> = mutableListOf()
)