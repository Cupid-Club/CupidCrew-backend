package cupidcrew.backend.api.config

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionDisconnectEvent

@Component
//@RequiredArgsConstructor
class WebSocketEventListener {

    @EventListener
    fun handleWebSocketDisconnectListener(
        event: SessionDisconnectEvent
    ) {
        //TO-DO

    }

}