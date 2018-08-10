package user.management.vn.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="user_detail")
public class UserDetail {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "fullname", nullable = false)
	private String fullname;

	@Column(name = "phone", columnDefinition = "VARCHAR(15)", nullable = false)
	private String phone;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "birthday",nullable = false)
	private Date birthDay;
	
	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "gender", columnDefinition = "TINYINT(1)", nullable = false)
	private Boolean gender;

	@Column(name = "created_at",columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdAt;

	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@PrimaryKeyJoinColumn(foreignKey=@ForeignKey(name="ref_user_detail"))
	private User userId;

	public UserDetail() {
		super();
	}

	public UserDetail(String fullname, String phone, String address, Boolean gender) {
		super();
		this.fullname = fullname;
		this.phone = phone;
		this.address = address;
		this.gender = gender;
	}

	public UserDetail(Long id, String fullname, String phone, String address, Boolean gender, Date createdAt,
			User userId) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.phone = phone;
		this.address = address;
		this.gender = gender;
		this.createdAt = createdAt;
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

}
