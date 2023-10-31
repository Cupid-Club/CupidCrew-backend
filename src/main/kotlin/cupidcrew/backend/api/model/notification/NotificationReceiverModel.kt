package cupidcrew.backend.api.model.notification

data class NotificationReceiverModel(
    val id: Long,
    val firebaseToken: String,
    val message: String,
)
