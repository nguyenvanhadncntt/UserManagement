package user.management.vn.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import user.management.vn.entity.Group;
import user.management.vn.entity.User;
import user.management.vn.entity.UserGroup;
import user.management.vn.repository.UserGroupRepository;
import user.management.vn.repository.UserRepository;
import user.management.vn.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserGroupRepository UserGroupRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> getAllUserOfGroup(Long groupId) {
		List<UserGroup> userGroups = UserGroupRepository.findAllUserOfGroupId(groupId);
		return convertUserGroupsToUsers(userGroups);
	}
	
	public List<User> convertUserGroupsToUsers(List<UserGroup> userGroups){
		List<User> listUser = new ArrayList<User>();
		for (UserGroup userGroup : userGroups) {
			listUser.add(userGroup.getUser());
		}
		return listUser;
	}

}
