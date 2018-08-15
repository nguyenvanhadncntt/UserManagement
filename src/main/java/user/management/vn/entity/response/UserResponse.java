package user.management.vn.entity.response;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import user.management.vn.entity.User;
import user.management.vn.entity.UserDetail;

public class UserResponse implements Serializable {

	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 1L;

	private Long id;
	private String email;	
	private Boolean enable;
	private Boolean nonLocked;
	private String fullname;
	private String phone;
	private Date birthday;
	private String address;
	private Boolean gender;
	private Date createdAt;

	public UserResponse() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Boolean getNonLocked() {
		return nonLocked;
	}

	public void setNonLocked(Boolean nonLocked) {
		this.nonLocked = nonLocked;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void addPropertiesFromUser(User user) {
		UserDetail userDetail = user.getUserDetail();
    
		this.email = user.getEmail();
		this.enable = user.getEnable();
		this.nonLocked = user.getNonLocked();
		this.id = user.getId();
		this.fullname = userDetail.getFullname();
		this.address = userDetail.getAddress();
		this.phone = userDetail.getPhone();
		this.birthday = userDetail.getBirthDay();
		this.gender = userDetail.getGender();
		this.createdAt = userDetail.getCreatedAt();
	}

}
