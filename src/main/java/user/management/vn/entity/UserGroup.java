package user.management.vn.entity;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "user_group")
public class UserGroup {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false,foreignKey=@ForeignKey(name="ref_user_group"))
	private User userId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "group_id", nullable = false,foreignKey=@ForeignKey(name="ref_group_user"))
	private Group groupId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "join_date", nullable = false)
	@CreationTimestamp
	private Date joinDate;

	public UserGroup() {
		super();
	}

	public UserGroup(User userId, Group groupId) {
		super();
		this.userId = userId;
		this.groupId = groupId;
	}
	
	public UserGroup(Long id, User userId, Group groupId, Date joinDate) {
		super();
		this.id = id;
		this.userId = userId;
		this.groupId = groupId;
		this.joinDate = joinDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public Group getGroupId() {
		return groupId;
	}

	public void setGroupId(Group groupId) {
		this.groupId = groupId;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	
}
