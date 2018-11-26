package user.management.vn.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import user.management.vn.entity.User;
import user.management.vn.service.UserService;
import user.management.vn.websocket.model.UserInfor;

@Component
public class WebSocketEventListener {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ListUserConnect listUserConnect;
	
	@EventListener
	public void connectListener(SessionConnectedEvent event) {
		String userName = event.getUser().getName();
		User user = userService.getUserByEmail(userName);
		listUserConnect.addUserConnect(userName, UserInfor.convertFromEntity(user));
	}
	
	@EventListener
	public void disconnectListener(SessionDisconnectEvent event) {
		String userName = event.getUser().getName();
		listUserConnect.removeUserDisconnect(userName);
	}
}
