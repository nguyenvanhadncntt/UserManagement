package user.management.vn.service;

import java.util.List;

import user.management.vn.entity.UserGroup;

public interface GroupService {
	public UserGroup addNewUserToGroup(Long groupId,Long userId);
	public boolean removeUseFromGroup(Long groupId, List<Long> userIds);
}
