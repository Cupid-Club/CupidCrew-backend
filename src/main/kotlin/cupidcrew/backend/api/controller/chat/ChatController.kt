package cupidcrew.backend.api.controller.chat

import cupidcrew.backend.api.model.chat.ChatMessage
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "[Crew-chat]", description = "crew 간 채팅 관련 api들")
@RestController
@RequestMapping("/crew")
class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    fun sendMessage (
        @RequestBody chatMessage: ChatMessage
    ) : ChatMessage {
        return chatMessage
    }

    @MessageMapping("/chat/addUser")
    @SendTo("/topic/public")
    fun addUser(
        @RequestBody chatMessage: ChatMessage,
        headerAccessor: SimpleMessageHeaderAccessor
    ) : ChatMessage {
        // add username in websocket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.sender)
        return chatMessage
    }

}