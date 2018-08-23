package user.management.vn.service;

import java.util.List;

import user.management.vn.entity.Role;
/**
 * 
 * @summary handle service of role
 * @author ThaiLe
 *
 */
public interface RoleService {
	
	/**
	* @summary return list of all role in database
	* @date Aug 15, 2018
	* @author ThaiLe
	* @return List<Role>
	 */
	List<Role> getAllRoles();
	
	/**
	* @summary return list of role base on roleName
	* @date Aug 15, 2018
	* @author ThaiLe
	* @param roleName
	* @return List<Role>
	 */
	List<Role> getRolesByNameContaining(String roleName);
	
	/**
	* @summary delete role base id
	* @date Aug 15, 2018
	* @author ThaiLe
	* @param id
	* @return boolean
	 */
	boolean deleteRoleById(Long id);
	
	/**
	* @summary add a role into database
	* @date Aug 15, 2018
	* @author ThaiLe
	* @param role
	* @return Role
	 */
	Role addRole(Role role);
	
	/**
	* @summary edit a role from database
	* @date Aug 15, 2018
	* @author ThaiLe
	* @param role
	* @return Role
	 */	
	Role editRole(Role role);	
	
	/**
	* @summary return Role base on id of role
	* @date Aug 15, 2018
	* @author ThaiLe
	* @param id
	* @return Role
	 */
	Role getRoleById(Long id);
	
	/**
	* @summary find Role base roleName of role
	* @date Aug 15, 2018
	* @author ThaiLe
	* @param roleName
	* @return Role
	 */
	Role findByRoleName(String roleName);
	
	/**
	* 
	* @summary List all Role co group id
	* @date Aug 17, 2018
	* @author Tai
	* @param group
	* @return
	* @return List<Role>
	 */
	List<Role> findByGroupRolesGroupId(long group);
  
  /**
	 * @summary get list role base on scope 
	 * @date Aug 17, 2018
	 * @author Thehap Rok
	 * @param scope
	 * @return List<Role>
	 */
	List<Role> getListRoleByScope(String scope);
	
}
