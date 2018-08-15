package user.management.vn.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="role")
public class Role {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "role_name", nullable = false)
	private String roleName;

	@Column(name = "non_del", columnDefinition = "TINYINT(1) default 1", nullable = false)
	private Boolean nonDel = true;

	@JsonIgnoreProperties({"role","user"})
	@OneToMany(mappedBy = "role",cascade=CascadeType.ALL)
	private List<UserRole> userRoles;

	@JsonIgnoreProperties({"role","group"})
	@OneToMany(mappedBy = "role",cascade=CascadeType.ALL)
	private List<GroupRole> groupRoles;

	public Role() {
		super();
	}

	public Role(String roleName) {
		super();
		this.roleName = roleName;
	}

	public Role(Long id, String roleName, Boolean nonDel, List<UserRole> userRoles, List<GroupRole> groupRoles) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.nonDel = nonDel;
		this.userRoles = userRoles;
		this.groupRoles = groupRoles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Boolean getNonDel() {
		return nonDel;
	}

	public void setNonDel(Boolean nonDel) {
		this.nonDel = nonDel;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public List<GroupRole> getGroupRoles() {
		return groupRoles;
	}

	public void setGroupRoles(List<GroupRole> groupRoles) {
		this.groupRoles = groupRoles;
	}

	
}
