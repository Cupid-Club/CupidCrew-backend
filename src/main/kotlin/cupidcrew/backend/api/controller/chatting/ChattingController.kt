package cupidcrew.backend.api.controller.chatting

import cupidcrew.backend.api.model.chatting.ChatMessage
import cupidcrew.backend.api.service.chatting.ChatService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller

@Controller
class ChatController(
    private val simpMessagingTemplate: SimpMessagingTemplate,
    private val chatService: ChatService) {

//    @MessageMapping("/chat/{roomName}/addUser")
//    @SendTo("/topic/{roomName}")
//    fun addUser(@DestinationVariable roomName: String, message: ChatMessage): ChatMessage {
//        // 방이 없으면 생성하고 사용자를 추가
//        if (chatService.getChatRoom(roomName) == null) {
//            chatService.createChatRoom(roomName)
//        }
//        chatService.addUserToChatRoom(roomName, message.sender)
//        return message
//    }
//
//    @MessageMapping("/chat/{roomName}/sendMessage")
//    @SendTo("/topic/{roomName}")
//    fun sendMessage(@DestinationVariable roomName: String, message: ChatMessage): ChatMessage {
//        chatService.sendMessage(roomName, message)
//        return message
//    }
    @MessageMapping("/message") // /app/message
    @SendTo("/chatroom/public")
    fun receivePublicMessage(
        @Payload message: ChatMessage): ChatMessage {
        return message;
    }

    @MessageMapping("/private-message")
    fun receivePrivateMessage(@Payload message: ChatMessage): ChatMessage {
        simpMessagingTemplate.convertAndSendToUser(message.receiver, "/private", message) // /user/David/private
        return message
    }



}