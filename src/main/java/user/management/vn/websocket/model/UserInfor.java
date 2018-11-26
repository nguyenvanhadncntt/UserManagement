package user.management.vn.websocket.model;

import java.io.Serializable;

import user.management.vn.define.UserConnectStatus;
import user.management.vn.entity.User;

public class UserInfor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String avatar;
	private String name;
	private int status;
	private String id;
	private String email;
	
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public static UserInfor convertFromEntity(User user) {
		UserInfor userInfor = new UserInfor();
		userInfor.setAvatar(user.getUserDetail().getPathImage());
		userInfor.setId(String.valueOf(user.getId()));
		userInfor.setName(user.getUserDetail().getFullname());
		userInfor.setEmail(user.getEmail());
		userInfor.setStatus(UserConnectStatus.ONLINE.getValue());
		return userInfor;
	}
}
