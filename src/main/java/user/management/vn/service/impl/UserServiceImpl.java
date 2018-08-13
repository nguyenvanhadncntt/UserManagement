package user.management.vn.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import user.management.vn.entity.User;
import user.management.vn.entity.UserGroup;
import user.management.vn.entity.UserRole;
import user.management.vn.entity.response.UserResponse;
import user.management.vn.repository.UserGroupRepository;
import user.management.vn.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserGroupRepository UserGroupRepository;
	
	@Override
	public List<UserResponse> getAllUserOfGroup(Long groupId) {
		List<UserGroup> userGroups = UserGroupRepository.findAllUserOfGroupId(groupId);
		List<User> listUser = convertUserGroupsToUsers(userGroups);
		return convertUserToUserResponse(listUser);
	}
	
	@Override
	public List<User> convertUserGroupsToUsers(List<UserGroup> userGroups){
		List<User> listUser = new ArrayList<User>();
		for (UserGroup userGroup : userGroups) {
			listUser.add(userGroup.getUser());
		}
		return listUser;
	}

	@Override
	public List<User> convertUserRolesToUsers(List<UserRole> userRoles) {
		List<User> listUser = new ArrayList<User>();
		for (UserRole userRole : userRoles) {
			listUser.add(userRole.getUser());
		}
		return listUser;
	}

	@Override
	public List<User> findUserNotInGroupByName(Long groupId,String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserResponse> convertUserToUserResponse(List<User> listUser) {
		List<UserResponse> listUserResponse = new ArrayList<>();
		for (User user : listUser) {
			UserResponse userResponse = new UserResponse();
			userResponse.addPropertiesFromUser(user);
			listUserResponse.add(userResponse);
		}
		return listUserResponse;
	}

}
