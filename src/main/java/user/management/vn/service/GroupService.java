package user.management.vn.service;

import java.util.List;
import java.util.Optional;

import user.management.vn.entity.Group;
import user.management.vn.entity.GroupRole;
import user.management.vn.entity.Role;
import user.management.vn.entity.UserGroup;

import user.management.vn.entity.response.UserResponse;

  /**
 * 
 * @summary interface group service
 * @date Aug 16, 2018
 * @author tai
 * @return
 */
public interface GroupService {
	/**
	 * @summary add user to group
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param userId
	 * @return UserGroup
	 */
	UserGroup addNewUserToGroup(Long groupId,Long userId);
	
	/**
	 * @summary remove user in group 
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param userId
	 * @return Integer
	 */
	Integer removeUserFromGroup(Long groupId,Long userId);
	
	/**
	 * @summary remove multi user in group  
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param userIds
	 * @return Integer
	 */
	Integer removeListUseFromGroup(Long groupId, List<Long> userIds);
	
	/**
	 * @summary  
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param nameOrEmail
	 * @return List<UserResponse>
	 */
	List<UserResponse> findUserNotInGroupByNameOrEmail(Long groupId,String nameOrEmail);
	
	/**
	 * @summary get infor of user in group 
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param userId
	 * @return
	 * @return UserResponse
	 */
	UserResponse getInforOfUser(Long groupId, Long userId);
  

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
