package user.management.vn.service;

import java.util.List;

import user.management.vn.entity.GroupRole;
import user.management.vn.entity.Role;

public interface RoleGroupService {

	/**
	 * 
	* @summary search all role in group
	* @date Aug 16, 2018
	* @author Tai
	* @param groupId
	* @return
	* @return List<GroupRole>
	 */
	List<GroupRole> findAllRoleByGroup(Long groupId);
	/**
	 * 
	* @summary check group have role ?
	* @date Aug 16, 2018
	* @author Tai
	* @param groupId
	* @return
	* @return boolean
	 */
	Boolean existsByGroup(Long groupId);
	/**
	 * 
	* @summary convert list groupRole object to role object
	* 
	* @date Aug 16, 2018
	* @author Tai
	* @param groupRole
	* @return
	* @return List<Role>
	 */
	List<Role> convertGroupRoleToRole(List<GroupRole> groupRole);
	/**
	 * 
	* @summary check role have in group ?
	* @date Aug 16, 2018
	* @author Tai
	* @param groupId
	* @param roleId
	* @return
	* @return boolean
	 */
	Boolean existsByGroupAndRole(Long groupId,Long roleId);
	/**
	 * 
	* @summary add role to group
	* @date Aug 16, 2018
	* @author Tai
	* @param groupId
	* @param roleId
	* @return
	* @return GroupRole
	 */
	GroupRole addRoleToGroup(Long groupId,Long roleId);
	
	/**
	 * 
	* @summary delete role in group
	* @date Aug 16, 2018
	* @author Tai
	* @param groupId
	* @param roleId
	* @return
	* @return boolean
	 */
	Boolean deleteRoleFormGroup(Long groupId, Long roleId);
}
