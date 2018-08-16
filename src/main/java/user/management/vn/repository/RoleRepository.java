package user.management.vn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
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
}
