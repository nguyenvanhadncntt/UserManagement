package user.management.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import user.management.vn.entity.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
	
}
