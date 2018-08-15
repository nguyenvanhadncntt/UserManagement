package user.management.vn.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import user.management.vn.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	List<Role> findByRoleNameContaining(String name);
	Role findByRoleName(String roleName);
	@Modifying
	@Query(value = "update role set non_del = 0 where id=?1", nativeQuery = true)
	int deleteRole(Long id);
	
	
}
