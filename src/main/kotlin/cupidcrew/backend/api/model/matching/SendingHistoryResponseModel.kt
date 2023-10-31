package cupidcrew.backend.api.model.matching

data class SendingHistoryResponseModel(
    val senderId: Long?,
    val receiverId: Long?,
    val senderCandidateId: Long?,
    val receiverCandidateId: Long?,
)
