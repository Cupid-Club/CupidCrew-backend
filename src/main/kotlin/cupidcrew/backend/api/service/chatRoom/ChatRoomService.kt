package cupidcrew.backend.api.service.chatRoom

import cupidcrew.backend.api.dto.chatRoom.ChatRoomRequestDto
import cupidcrew.backend.api.dto.chatRoom.MessageRequestDto
import cupidcrew.backend.api.mapper.chatRoom.ChatRoomMapper
import cupidcrew.backend.api.repository.chatRoom.ChatRoomRepository
import cupidcrew.backend.api.repository.chatRoom.MessageRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatRoomService(
    private val chatRoomMapper: ChatRoomMapper,
    private val chatRoomRepository: ChatRoomRepository,
    private val messageRepository: MessageRepository
) {

    @Transactional
    fun createChatRoom(chatRoomDto: ChatRoomRequestDto): ChatRoomRequestDto {
        val chatRoomEntity = chatRoomMapper.toEntity(chatRoomDto)
        chatRoomRepository.save(chatRoomEntity)
        return chatRoomMapper.toDto(chatRoomEntity)
    }
//
    @Transactional
    fun addMessageToChatRoom(messageRequestDto: MessageRequestDto): MessageRequestDto {
        val chatRoom = chatRoomRepository.findByChatRoomId(messageRequestDto.chatRoomId)
        val messageEntity = chatRoomMapper.toEntity(messageRequestDto)
        messageRepository.save(messageEntity)
        return chatRoomMapper.toDto(messageEntity)
    }
}
//
//    fun getAllChatRooms(): List<ChatRoomDTO> =
//        chatRoomRepository.findAll().map { toDTO(it) }
//
//    private fun toDTO(chatRoom: ChatRoom): ChatRoomDTO =
//        ChatRoomDTO(
//            id = chatRoom.id,
//            senderId = chatRoom.senderId,
//            receiverId = chatRoom.receiverId,
//            creationDate = chatRoom.creationDate,
//            messages = chatRoom.messages.map { toDTO(it) }
//        )
//
//    private fun toDTO(message: Message): MessageDTO =
//        MessageDTO(
//            id = message.id,
//            senderId = message.senderId,
//            content = message.content,
//            creationDate = message.creationDate
//        )
//}
