package user.management.vn.service;

import java.util.List;

import user.management.vn.entity.User;
import user.management.vn.entity.UserGroup;
import user.management.vn.entity.UserRole;

public interface UserService {
	List<User> convertUserGroupsToUsers(List<UserGroup> userGroups);
	List<User> convertUserRolesToUsers(List<UserRole> userRoles);
	List<User> getAllUserOfGroup(Long groupId);
	List<User> findUserNotInGroupByName(Long groupId,String name);
}
