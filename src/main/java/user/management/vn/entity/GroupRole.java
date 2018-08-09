package user.management.vn.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "role_group")
public class GroupRole {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "role_id", nullable = false, foreignKey = @ForeignKey(name = "ref_role_group"))
	private Role roleId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "group_id", nullable = false, foreignKey = @ForeignKey(name = "ref_group_role"))
	private Group groupId;

	public GroupRole() {
		super();
	}

	public GroupRole(Role roleId, Group groupId) {
		super();
		this.roleId = roleId;
		this.groupId = groupId;
	}

	public GroupRole(Long id, Role roleId, Group groupId) {
		super();
		this.id = id;
		this.roleId = roleId;
		this.groupId = groupId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role getRoleId() {
		return roleId;
	}

	public void setRoleId(Role roleId) {
		this.roleId = roleId;
	}

	public Group getGroupId() {
		return groupId;
	}

	public void setGroupId(Group groupId) {
		this.groupId = groupId;
	}

}
