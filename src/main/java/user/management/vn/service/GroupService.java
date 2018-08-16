package user.management.vn.service;

import java.util.List;
import java.util.Optional;

import user.management.vn.entity.Group;
import user.management.vn.entity.GroupRole;
import user.management.vn.entity.Role;
import user.management.vn.entity.UserGroup;
/**
 * 
 * @summary interface group service
 * @date Aug 16, 2018
 * @author tai
 * @return
 */
public interface GroupService {
	/**
	* @summary view all group
	* @date Aug 16, 2018
	* @author Tai
	* @return List<Group>
	 */
	List<Group> viewAllGroup();
	/**
	* @summary view a group
	* @date Aug 16, 2018
	* @author Tai
	* @param groupId
	* @return Optional<Group>
	 */
	Optional<Group> viewGroup(long groupId);
	/**
	 * 
	* @summary add a group 
	* @date Aug 16, 2018
	* @author Tai
	* @param group
	* @return Group
	 */
	Group addGroup(Group group);
	/**
	 * 
	* @summary save a group
	* @date Aug 16, 2018
	* @author Tai
	* @param group 
	* @return Group
	 */
	Group saveGroup(Group group);
	/**
	 * 
	* @summary delete a group base on id 
	* @date Aug 16, 2018
	* @author Tai
	* @param groupId
	* @return
	* @return Optional<Group>
	 */
	Optional<Group> deleteGroup(long groupId);
	
	UserGroup addNewUserToGroup(Long groupId,Long userId);
}
