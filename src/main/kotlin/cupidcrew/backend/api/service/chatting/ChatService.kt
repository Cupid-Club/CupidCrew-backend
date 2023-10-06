package cupidcrew.backend.api.service.chatting

import cupidcrew.backend.api.model.chatting.ChatMessage
import cupidcrew.backend.api.model.chatting.ChatRoom
import org.springframework.stereotype.Service

@Service
class ChatService {
    private val chatRooms = mutableMapOf<String, ChatRoom>()

    fun createChatRoom(roomName: String) {
        chatRooms[roomName] = ChatRoom(name = roomName)
    }

    fun addUserToChatRoom(roomName: String, username: String) {
        val chatRoom = chatRooms[roomName]
        chatRoom?.participants?.add(username)
    }

    fun sendMessage(roomName: String, message: ChatMessage) {
        val chatRoom = chatRooms[roomName]
        chatRoom?.messages?.add(message)
    }

    fun getChatRoom(roomName: String): ChatRoom? {
        return chatRooms[roomName]
    }
}
