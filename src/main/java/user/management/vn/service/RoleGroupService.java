package user.management.vn.service;

import java.util.List;

import user.management.vn.entity.Group;
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
	List<GroupRole> findAllGroupByRole(Long roleId);
	
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
	Boolean existsByRole(long roleId);
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
	List<Group> convertGroupRoleToGroup(List<GroupRole> groupRole);
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
	GroupRole addGroupToRole(Long groupId,Long roleId);
	
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
	Boolean deleteGroupFormRole(Long groupId, Long roleId);
	/**
	 * 
	* @summary delete all role in group
	* @date Aug 16, 2018
	* @author Tai
	* @param groupId
	* @param roleId
	* @return
	* @return Integer
	 */
	Boolean deleteAllRoleFromGroup(Long groupId, List<Role> role);
	
	Integer deleteListRoleFromGroup(Long groupId, List<Long> roleIds);
	Integer deleteListGroupFromRole(Long roleId, List<Long> groupIds);
}
