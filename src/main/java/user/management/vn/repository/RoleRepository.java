package user.management.vn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import user.management.vn.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	/**
	* @summary find by group id in table GroupRole
	* @date Aug 16, 2018
	* @author bom
	* @param group
	* @return
	* @return List<Role>
	 */
	List<Role> findByGroupRoles_Group(long group);
	
	/**
	* @summary find role by id
	* @date Aug 16, 2018
	* @author Tai
	* @param id role
	* @param nonDel 
	* @return
	* @return Role
	 */
	Role findByIdAndNonDel(long id,boolean nonDel);
  
  
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
