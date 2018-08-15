package user.management.vn.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import user.management.vn.entity.UserRole;

@Repository
@Transactional
public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
	
}
