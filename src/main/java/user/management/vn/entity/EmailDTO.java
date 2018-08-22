package user.management.vn.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class EmailDTO {

	@Email
	@NotBlank(message = "You must not be blank")
	private String email;

	public EmailDTO() {
		super();
	}

	public EmailDTO(@Email @NotBlank String email) {
		super();
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
