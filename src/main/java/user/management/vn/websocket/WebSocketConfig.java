package user.management.vn.websocket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import user.management.vn.entity.Group;
import user.management.vn.repository.GroupRepository;

@EnableWebSocketMessageBroker
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/wschat").setAllowedOrigins("*").withSockJS();
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/group");
		List<Group> list = groupRepository.findAll();
		List<String> listGroupId = new ArrayList<String>(); 
		for (Group group : list) {
			listGroupId.add("/"+String.valueOf(group.getId()));
		}
		registry.enableSimpleBroker(listGroupId.toArray(new String[listGroupId.size()]));
	}
	
}
