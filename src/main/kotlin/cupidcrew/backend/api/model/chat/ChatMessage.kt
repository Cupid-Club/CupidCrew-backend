package cupidcrew.backend.api.model.chat

import cupidcrew.backend.api.model.MessageType

data class ChatMessage(
    val sender: String,
    val content: String,
    val type: MessageType,
)
