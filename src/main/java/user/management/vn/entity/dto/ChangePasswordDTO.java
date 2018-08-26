package user.management.vn.entity.dto;

import javax.validation.constraints.NotBlank;

public class ChangePasswordDTO {
	
	private String token;
	private String email;
	@NotBlank
	private String password;	
	private String matchingPassword;
	public ChangePasswordDTO() {
		super();
	}
	public ChangePasswordDTO(@NotBlank String password, String matchingPassword) {
		super();
		this.password = password;
		this.matchingPassword = matchingPassword;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMatchingPassword() {
		return matchingPassword;
	}
	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
	
}
