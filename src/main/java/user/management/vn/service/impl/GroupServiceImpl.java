package user.management.vn.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import user.management.vn.entity.Group;
import user.management.vn.entity.User;
import user.management.vn.entity.UserGroup;
import user.management.vn.repository.GroupRepository;
import user.management.vn.repository.UserGroupRepository;
import user.management.vn.repository.UserRepository;
import user.management.vn.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private UserGroupRepository userGroupRepository;
	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserGroup addNewUserToGroup(Long groupId, Long userId) {
		Optional<Group> groupOptional = groupRepository.findById(groupId);
		Optional<User> userOptional = userRepository.findById(userId);
		if (!groupOptional.isPresent() || !userOptional.isPresent()) {
			return null;
		}
		UserGroup userGroup = new UserGroup(userOptional.get(), groupOptional.get());
		return userGroupRepository.save(userGroup);
	}

	@Override
	public Integer removeListUseFromGroup(Long groupId, List<Long> userIds) {
		Optional<Group> groupOptional = groupRepository.findById(groupId);
		if (!groupOptional.isPresent()) {
			return 0;
		}
		for (Long userId : userIds) {
			System.out.println(userId);
			userGroupRepository.deleteUserFromGroup(groupId, userId);
		}
		return 1;

	}

	@Override
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
		return 1;
	}

	@Override
	public List<User> findUserNotInGroupByNameOrEmail(Long groupId, String nameOrEmail) {
		List<User> listUser = userRepository.findUserNotInGroupByNameOrEmail(groupId, nameOrEmail);
		return listUser;
	}

}
