package user.management.vn.service;

import java.util.List;

import user.management.vn.entity.User;
import user.management.vn.entity.UserGroup;
import user.management.vn.entity.UserRole;
import user.management.vn.entity.response.UserResponse;

public interface UserService {
	List<User> convertUserGroupsToUsers(List<UserGroup> userGroups);
	List<User> convertUserRolesToUsers(List<UserRole> userRoles);
	List<UserResponse> convertUserToUserResponse(List<User> listUser);
	List<UserResponse> getAllUserOfGroup(Long groupId);
}
