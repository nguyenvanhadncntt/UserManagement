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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "block_user")
public class BlockUser {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", 
				foreignKey = @ForeignKey(name = "ref_blockuser_user"), 
				unique = true, 
				nullable = false)
	private User userId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "block_time", nullable = false)
	private Date blockTime;

	@Column(name = "number_fail", nullable = false)
	private Integer numberFail;

	public BlockUser() {
		super();
	}

	public BlockUser(User userId, Date blockTime, Integer numberFail) {
		super();
		this.userId = userId;
		this.blockTime = blockTime;
		this.numberFail = numberFail;
	}

	public BlockUser(Long id, User userId, Date blockTime, Integer numberFail) {
		super();
		this.id = id;
		this.userId = userId;
		this.blockTime = blockTime;
		this.numberFail = numberFail;
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

	public Date getBlockTime() {
		return blockTime;
	}

	public void setBlockTime(Date blockTime) {
		this.blockTime = blockTime;
	}

	public Integer getNumberFail() {
		return numberFail;
	}

	public void setNumberFail(Integer numberFail) {
		this.numberFail = numberFail;
	}

}
