package user.management.vn.websocket;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import user.management.vn.websocket.model.UserInfor;

@Component
public class ListUserConnect {
	private Map<String,UserInfor> listUserConnect = new HashMap<>();
	
	public void addUserConnect(String userName, UserInfor userInfor) {
		if(!listUserConnect.containsKey(userName)) {
			listUserConnect.put(userName, userInfor);
		}
	}

	public void removeUserDisconnect(String userName) {
		if(listUserConnect.containsKey(userName)) {
			listUserConnect.remove(userName);
		}
	}
	
	public Map<String, UserInfor> getListUserConnect() {
		return listUserConnect;
	}

	public void setListUserConnect(Map<String, UserInfor> listUserConnect) {
		this.listUserConnect = listUserConnect;
	}
	
}
