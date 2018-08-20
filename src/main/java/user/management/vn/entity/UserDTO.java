package user.management.vn.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
<<<<<<< HEAD

import user.management.vn.validation.PasswordMatches;

@PasswordMatches
=======
/**
 * 
 * @author ThaiLe
 * get user infor from request
 *
 */
>>>>>>> 51d2335ca36818a73c1bf15c35d4e11fa894c37d
public class UserDTO implements Serializable{
	
	@JsonIgnore
	private static final long serialVersionUID = 1L;	
	@Email
	@NotNull
	@NotEmpty
	private String email;
	@NotNull
	private String fullname;
	private String phone;
	@NotNull
	private Date birthday;
	@NotNull
	private String address;
	@NotNull
	private Boolean gender;
	@NotNull
	@NotEmpty
	private String password;	
	private String matchingPassword;
	
	public String getMatchingPassword() {
		return matchingPassword;
	}
	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}
	public UserDTO() {
		super();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Boolean getGender() {
		return gender;
	}
	public void setGender(Boolean gender) {
		this.gender = gender;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
