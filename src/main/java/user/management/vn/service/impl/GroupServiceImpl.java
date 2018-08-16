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
	public List<Group> viewAllGroup() {
		
		return groupRepository.findByNonDel(true);
	}

	@Override
	public Group addGroup(Group group) {
		
		return groupRepository.save(group);
	}
	

	@Override
	public Optional<Group> viewGroup(long groupId) {
		// TODO Auto-generated method stub
		return groupRepository.findByIdAndNonDel(groupId, true);
	}

//	@Override
//	public Optional<Group> editGroup(long groupId) {
//		Optional<Group> group = groupRepository.findByIdAndNonDel(groupId, true);
//		return Optional.ofNullable(groupRepository.save(group.get()));
//		
//	}

	@Override
	public Group saveGroup(Group group) {
		
		return groupRepository.save(group);
	}

	@Override
	public Optional<Group> deleteGroup(long groupId) {
		
		Optional<Group> group = groupRepository.findByIdAndNonDel(groupId, true);
		group.get().setNonDel(false);
		
		return Optional.ofNullable(groupRepository.save(group.get()));
	}
	
	
	
	
}
