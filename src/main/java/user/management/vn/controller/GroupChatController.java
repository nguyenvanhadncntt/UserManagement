package user.management.vn.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import user.management.vn.websocket.model.ChatMessage;

@Controller
public class GroupChatController {
	
	@MessageMapping("/{groupId}/conversation")
	@SendTo("/group/{groupId}")
	public ChatMessage sendMessageToGroup(@DestinationVariable(value="groupId") String groupId,
			@Payload ChatMessage message) {
		return message;
	}
	
}
