package cupidcrew.backend.api.mapper.matching

import cupidcrew.backend.api.dao.matching.MatchingEntity
import cupidcrew.backend.api.dto.matching.SendingHistoryRequestDto
import cupidcrew.backend.api.model.matching.SendingHistoryRequestModel
import cupidcrew.backend.api.model.matching.SendingHistoryResponseModel
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.springframework.stereotype.Component

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
abstract class MatchingMapper {

    abstract fun toDto(model: SendingHistoryRequestModel): SendingHistoryRequestDto

    abstract fun toEntity(dto: SendingHistoryRequestDto): MatchingEntity

    abstract fun toDto(entity: MatchingEntity): SendingHistoryRequestDto

    abstract fun toModel(dto: SendingHistoryRequestDto): SendingHistoryResponseModel
}