package user.management.vn.service;

import java.util.List;

import user.management.vn.entity.UserGroup;
import user.management.vn.entity.response.UserResponse;

/**
 * handle service of group
 * @author Thehap Rok
 *
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
}
