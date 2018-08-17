package user.management.vn.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import user.management.vn.entity.UserRole;

@Repository
@Transactional
public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
	Boolean existsByUserIdAndRoleId(Long userId,Long roleId);
	
	List<UserRole> findByUserId(Long userId);
	
	@Modifying
	@Query(value= "delete from user_role where user_id=?1 and role_id =?2",nativeQuery=true)
	Integer deleteByUserIdAndRoleId(Long userId,Long roleId);
}
