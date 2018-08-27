package user.management.vn.entity.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import user.management.vn.entity.Role;
import user.management.vn.entity.User;
import user.management.vn.entity.UserDetail;
import user.management.vn.entity.UserRole;
import user.management.vn.validation.Phone;

/**
 * 
 * @author Thehap Rok
 *
 */
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
	
	@NotBlank
	private String fullname;
	
	@Phone
	private String phone;
	
	@NotNull
	private Date birthday;
	
	@NotBlank
	private String address;
	
	@NotNull
	private Boolean gender;
	private Date createdAt;

	@JsonIgnoreProperties(value = { "userRoles", "groupRoles" })
	private List<Role> listRole;

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
	
	public List<Role> getListRole() {
		return listRole;
	}

	public void setListRole(List<Role> listRole) {
		this.listRole = listRole;
	}
	
	/**
	 * @summary add properties of user to user response
	 * @param user
	 */
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
		
		this.listRole = new ArrayList<>();
		List<UserRole> listUR = user.getUserRoles();
		for (UserRole userRole : listUR) {
			this.listRole.add(userRole.getRole());
		}
		
	}

}
