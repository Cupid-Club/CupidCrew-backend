package cupidcrew.backend.api.model.chatting

data class ChatMessage(
    val sender: String,
    val receiver: String,
    val content: String,
    val date: String,
    val status: Status,
)