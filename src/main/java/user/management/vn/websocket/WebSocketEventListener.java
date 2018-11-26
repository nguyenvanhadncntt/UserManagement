package user.management.vn.websocket;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
public class WebSocketEventListener {
	
	@EventListener
	public void connectListener(SessionConnectedEvent event) {
		System.err.println(event.getUser().getName());
	}
}
