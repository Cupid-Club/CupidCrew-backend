package cupidcrew.backend.api.service.matching

import cupidcrew.backend.api.dto.matching.SendingHistoryRequestDto
import cupidcrew.backend.api.mapper.matching.MatchingMapper
import cupidcrew.backend.api.repository.matching.MatchingRepository
import org.springframework.stereotype.Service

@Service
class MatchingService(
    private val matchingRepository: MatchingRepository,
    private val matchingMapper: MatchingMapper,
) {

    fun createHistory(historyDto: SendingHistoryRequestDto) : SendingHistoryRequestDto {
        val matchingEntity = matchingMapper.toEntity(historyDto)
        return matchingMapper.toDto(matchingRepository.save(matchingEntity))
    }



}