package cupidcrew.backend.api.service.notification

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import org.springframework.stereotype.Service

@Service
class NotificationService {

    fun sendNotification(firebaseToken: String, message: String) {
        val message = Message.builder()
            .putData("content", message)
            .setToken(firebaseToken)
            .build()

        FirebaseMessaging.getInstance().send(message)
    }
}