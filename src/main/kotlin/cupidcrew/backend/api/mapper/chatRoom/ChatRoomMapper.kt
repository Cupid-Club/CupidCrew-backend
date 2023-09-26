package cupidcrew.backend.api.mapper.chatRoom

import ChatRoomEntity
import cupidcrew.backend.api.dao.chatRoom.MessageEntity
import cupidcrew.backend.api.dto.chatRoom.ChatRoomRequestDto
import cupidcrew.backend.api.dto.chatRoom.MessageRequestDto
import cupidcrew.backend.api.model.chatRoom.ChatRoomRequestModel
import cupidcrew.backend.api.model.chatRoom.MessageRequestModel
import org.aspectj.bridge.Message
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.springframework.stereotype.Component

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
abstract class ChatRoomMapper {

    // signup
    abstract fun toDto(model: ChatRoomRequestModel): ChatRoomRequestDto

    abstract fun toDto(model: MessageRequestModel): MessageRequestDto

    abstract fun toEntity(dto: ChatRoomRequestDto): ChatRoomEntity
    abstract fun toEntity(dto: MessageRequestDto): MessageEntity


    abstract fun toDto(entity: ChatRoomEntity): ChatRoomRequestDto
//
    abstract fun toDto(entity: MessageEntity): MessageRequestDto
}