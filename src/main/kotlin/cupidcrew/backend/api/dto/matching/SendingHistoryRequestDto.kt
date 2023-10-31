package cupidcrew.backend.api.dto.matching

data class SendingHistoryRequestDto(
    val senderId: Long,
    val receiverId: Long,
    val senderCandidateId: Long,
    val receiverCandidateId: Long,
    var status: String? = "unchecked",
    var result: String? = "unmatched",
)
