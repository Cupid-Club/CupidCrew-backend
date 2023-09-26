package cupidcrew.backend.api.controller.chatRoom

import cupidcrew.backend.api.mapper.chatRoom.ChatRoomMapper
import cupidcrew.backend.api.model.chatRoom.ChatRoomRequestModel
import cupidcrew.backend.api.model.chatRoom.MessageRequestModel
import cupidcrew.backend.api.service.chatRoom.ChatRoomService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "[Crew-chatting]", description = "crew 간 채팅 관련 api들")
@RestController
@RequestMapping("/crew/chat")
class ChatRoomController(
    private val chatRoomMapper: ChatRoomMapper,
    private val chatRoomService: ChatRoomService
) {

    @PostMapping("/room")
    fun createChatRoom(@RequestBody chatRoomRequestModel: ChatRoomRequestModel) {
        val chatRoomRequestDto = chatRoomMapper.toDto(chatRoomRequestModel)
        chatRoomService.createChatRoom(chatRoomRequestDto)
    }

    @PostMapping("/room/{roomId}/message")
    fun addMessageToChatRoom(
        @RequestBody  messageRequestModel: MessageRequestModel
    ) {
        val messageRequestDto = chatRoomMapper.toDto(messageRequestModel)
        chatRoomService.addMessageToChatRoom(messageRequestDto)
    }
//
//    @GetMapping("/rooms")
//    fun getAllChatRooms(): List<ChatRoomDto> = chatRoomService.getAllChatRooms()
//}

}