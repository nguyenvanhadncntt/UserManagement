package user.management.vn.service;

import java.util.List;

import user.management.vn.entity.UserGroup;

/**
 * @author Thehap Rok
 *
 */
public interface GroupService {
	
	UserGroup addNewUserToGroup(Long groupId,Long userId);
	Integer removeUserFromGroup(Long groupId,Long userId);
	Integer removeListUseFromGroup(Long groupId, List<Long> userIds);
}
