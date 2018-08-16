package user.management.vn.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import user.management.vn.entity.Group;
import user.management.vn.entity.GroupRole;
import user.management.vn.entity.Role;
import user.management.vn.exception.GroupNotFoundException;
import user.management.vn.exception.RoleNotFoundException;
import user.management.vn.repository.GroupRepository;
import user.management.vn.repository.GroupRoleRepository;
import user.management.vn.repository.RoleRepository;
import user.management.vn.service.RoleGroupService;

@Service
public class RoleGroupServiceImpl implements RoleGroupService {
	@Autowired
	GroupRepository groupRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	GroupRoleRepository groupRoleRepository;


	@Override
	public Boolean existsByGroup(Long groupId) {
		Optional<Group> exist = groupRepository.findByIdAndNonDel(groupId, false);
		if (!exist.isPresent()) {
			return false;
		}
		return true;
	}


	@Override
	public List<GroupRole> findAllRoleByGroup(Long groupId) {
		Optional<Group> groupOptional = groupRepository.findById(groupId);
		List<GroupRole> list = groupRoleRepository.findByGroup(groupOptional.get());
		for (GroupRole groupRole : list) {
			System.out.println(groupRole.getRole().getRoleName());
		}
		return list;
	}

	@Override
	public List<Role> convertGroupRoleToRole(List<GroupRole> groupRole) {

		List<Role> role = new ArrayList<>();
		for (GroupRole groupRole2 : groupRole) {
			role.add(groupRole2.getRole());
		}
		return role;
	}

	@Override
	public Boolean existsByGroupAndRole(Long groupId, Long roleId) {
		Optional<GroupRole> exist = groupRoleRepository.findByGroupIdAndRoleId(groupId, roleId);
		if (!exist.isPresent()) {
			return false;
		}
		return true;
	}

	@Override
	public GroupRole addRoleToGroup(Long groupId, Long roleId) {
		Optional<GroupRole> exist = groupRoleRepository.findByGroupIdAndRoleId(groupId, roleId);
		if (exist.isPresent()) {
			return null;
		}
		Optional<Group> group = groupRepository.findById(groupId);
		Optional<Role> role = roleRepository.findById(roleId);
		if (!group.isPresent()) {
			throw new GroupNotFoundException("Group Not Found id: " + groupId);
		} 
		if (!role.isPresent()) {
			throw new RoleNotFoundException("Role Not Found id: " + roleId);
		}
		
		GroupRole groupRole = new GroupRole();
		groupRole.setGroup(group.get());
		groupRole.setRole(role.get());
		
		return groupRoleRepository.save(groupRole);
	}

	@Override
	public Boolean deleteRoleFormGroup(Long groupId, Long roleId) {
		Boolean exist = existsByGroupAndRole(groupId, roleId);
		if (!exist) {
			return false;
		}
		Integer deleted = groupRoleRepository.deleteByGroupIdAndRoleId(groupId, roleId);
		return true;
	}

}
