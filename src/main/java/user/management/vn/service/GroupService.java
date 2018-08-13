package user.management.vn.service;

import java.util.List;
import java.util.Optional;

import user.management.vn.entity.Group;
import user.management.vn.entity.UserGroup;

public interface GroupService {
	List<Group> viewAllGroup();
	Optional<Group> viewGroup(long groupId);
	Group addGroup(Group group);
//	Optional<Group> editGroup(long groupId);
	Group saveGroup(Group group);
	Optional<Group> deleteGroup(long groupId);
	UserGroup addNewUserToGroup(Long groupId,Long userId);
	boolean removeUseFromGroup(Long groupId, List<Long> userIds);
}
