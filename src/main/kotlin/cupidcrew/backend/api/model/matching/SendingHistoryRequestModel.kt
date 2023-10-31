package cupidcrew.backend.api.model.matching

data class SendingHistoryRequestModel(
    val senderId: Long?,
    val receiverId: Long?,
    val senderCandidateId: Long?,
    val receiverCandidateId: Long?,
)
