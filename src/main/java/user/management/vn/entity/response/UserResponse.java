package user.management.vn.entity.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserResponse implements Serializable{

	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String email;
	private Boolean enable;
	private Boolean nonDel;
}
