package user.management.vn.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import user.management.vn.entity.Group;
import user.management.vn.entity.GroupRole;
import user.management.vn.entity.User;
import user.management.vn.entity.UserGroup;
import user.management.vn.entity.UserRole;
import user.management.vn.entity.response.UserResponse;
import user.management.vn.repository.GroupRepository;
import user.management.vn.repository.GroupRoleRepository;
import user.management.vn.repository.UserGroupRepository;
import user.management.vn.repository.UserRepository;
import user.management.vn.repository.UserRoleRepository;
import user.management.vn.service.GroupService;
import user.management.vn.service.UserService;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private UserGroupRepository userGroupRepository;
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired

	private GroupRoleRepository groupRoleRepository;

	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserService userService;
	
	@Override
	public UserGroup addNewUserToGroup(Long groupId, Long userId) {
		Optional<Group> groupOptional = groupRepository.findByIdAndNonDel(groupId, true);
		Optional<User> userOptional = userRepository.findById(userId);
		if (!groupOptional.isPresent() || !userOptional.get().getEnable()) {
			return null;
		}
		if(!userOptional.isPresent() || !userOptional.get().getEnable()) {
			
		}
		boolean checkExist = userGroupRepository.existsByGroupIdAndUserId(groupId, userId);
		if (checkExist) {
			return null;
		}
		UserGroup userGroup = new UserGroup(userOptional.get(), groupOptional.get());
		addAllRoleOfGroupToUserRole(groupOptional.get(), userOptional.get());
		return userGroupRepository.save(userGroup);
	}


	@Override
  	public List<Group> viewAllGroup() {
		
		return groupRepository.findByNonDel(true);
	}

	@Override
	public Group addGroup(Group group) {
		
		return groupRepository.save(group);
	}
	

	@Override
	public Optional<Group> viewGroup(Long groupId) {
		// TODO Auto-generated method stub
		return groupRepository.findByIdAndNonDel(groupId, true);
  }
	@Transactional
	public Integer removeListUseFromGroup(Long groupId, List<Long> userIds) {
		Optional<Group> groupOptional = groupRepository.findById(groupId);
		if (!groupOptional.isPresent()) {
			return 0;
		}
		for (Long userId : userIds) {
			userGroupRepository.deleteUserFromGroup(groupId, userId);
		}
		return 1;

	}

	@Override
	@Transactional
	public Integer removeUserFromGroup(Long groupId, Long userId) {
		Optional<Group> groupOptional = groupRepository.findById(groupId);
		Optional<User> userOptional = userRepository.findById(userId);
		if (!groupOptional.isPresent()) {
			return 0;
		}
		if (!userOptional.isPresent()) {
			return 0;
		}
		userGroupRepository.deleteUserFromGroup(groupId, userId);
		userService.removeRoleOfGroupFromUserRole(groupOptional.get(),userOptional.get());
		return 1;
	}

	@Override
	public List<UserResponse> findUserNotInGroupByNameOrEmail(Long groupId, String nameOrEmail) {
		List<User> listUser = userRepository.findUserNotInGroupByNameOrEmail(groupId, nameOrEmail);
		List<UserResponse> users = userService.convertUserToUserResponse(listUser);
		return users;
	}

	@Override
	public UserResponse getInforOfUser(Long groupId, Long userId) {
		Optional<Group> groupOptional = groupRepository.findByIdAndNonDel(groupId, true);
		Optional<User> userOptional = userRepository.findById(userId);
		if (!groupOptional.isPresent() || groupOptional.get().getNonDel() != true) {
			return null;
		}
		if (!userOptional.isPresent() || userOptional.get().getNonDel() != true) {
			return null;
		}
		Optional<UserGroup> userGroupOptional = userGroupRepository.findUserById(groupId, userId);
		if (!userGroupOptional.isPresent()) {
			return null;
		}

		UserGroup ug = userGroupOptional.get();
		User user = ug.getUser();
		UserResponse userResponse = new UserResponse();
		userResponse.addPropertiesFromUser(user);
		return userResponse;
	}

	@Override
	public Group saveGroup(Group group) {
		
		return groupRepository.save(group);
	}

	@Override
	public Optional<Group> deleteGroup(Long groupId) {
		
		Optional<Group> group = groupRepository.findByIdAndNonDel(groupId, true);
		group.get().setNonDel(false);
		long deleteRoleGroup = groupRoleRepository.deleteByGroupId(groupId);
		
		return Optional.ofNullable(groupRepository.save(group.get()));
	}

	@Override
	public void addAllRoleOfGroupToUserRole(Group group, User user) {
		List<GroupRole> groupRoles = group.getGroupRoles();
		for (GroupRole groupRole : groupRoles) {
			Boolean checkExists = userRoleRepository.existsByUserIdAndRoleId(user.getId(), groupRole.getRole().getId());
			if(!checkExists) {
				UserRole ur = new UserRole(user, groupRole.getRole());  
				userRoleRepository.save(ur);
			}
		}
	}
	
}
