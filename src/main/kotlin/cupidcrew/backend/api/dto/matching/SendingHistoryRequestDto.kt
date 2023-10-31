package cupidcrew.backend.api.dto.matching

data class SendingHistoryRequestDto(
    val id: Long?,
    val senderId: Long?,
    val receiverId: Long?,
    val senderCandidateId: Long?,
    val receiverCandidateId: Long?,
    val status: String? = "unchecked",
    val result: String? = "unmatched",
)
