package user.management.vn.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import user.management.vn.entity.Group;
import user.management.vn.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	
	
	//@summary find by group id in table GroupRole
	List<Role> findByGroupRolesGroupId(long group);
	
	//@summary find role by id
	Optional<Role> findByIdAndNonDel(long id,boolean nonDel);
	
	
  
	Optional<Role> findById(Long roleId);
	
	List<Role> findByNonDel(Boolean nonDel);
	
	
  
	List<Role> findByRoleNameContaining(String name);

	Role findByRoleName(String roleName);

	/**
	 * @summary 
	 * @date Aug 17, 2018
	 * @author Thehap Rok
	 * @param id
	 * @return int
	 */
	@Modifying
	@Query(value = "update role set non_del = 0 where id=?1", nativeQuery = true)
	int deleteRole(Long id);
	
	/**
	 * @summary find role by scope (system or group) 
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param scope
	 * @return List<Role>
	 */
	List<Role> findByScope(String scope);
}
