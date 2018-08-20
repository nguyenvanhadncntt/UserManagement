package user.management.vn.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

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
import user.management.vn.util.RoleScope;

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
		Optional<Group> groupOptional = groupRepository.findByIdAndNonDel(groupId, true);
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
		Optional<Group> group = groupRepository.findByIdAndNonDel(groupId, true);
		Optional<Role> role = roleRepository.findByIdAndNonDel(roleId, true);
		if (exist.isPresent()) {
			return null;
		}
		if (!group.isPresent()) {
			throw new GroupNotFoundException("Group Not Found id: " + groupId);
		} 
		if (!role.isPresent()) {
			throw new RoleNotFoundException("Role Not Found id: " + roleId);
		}
		if (!role.get().getScope().equals(RoleScope.GROUP)) {
//			throw new
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


	@Override
	@Transactional
	public Boolean deleteAllRoleFromGroup(Long groupId, List<Role> role) {
		Optional<Group> groupOptional = groupRepository.findByIdAndNonDel(groupId,true);
		if (!groupOptional.isPresent()) {
			return false;
		}
		for ( Role deletedRole : role) {
			System.out.println(deletedRole.getId());
			groupRoleRepository.deleteByGroupIdAndRoleId(groupId, deletedRole.getId());
		}
		return true;
	}


	@Override
	public Integer deleteListRoleFromGroup(Long groupId, List<Long> roleIds) {
		Optional<Group> groupOptional = groupRepository.findByIdAndNonDel(groupId,true);
		if (!groupOptional.isPresent()) {
			return 0;
		}
		for (Long roleId : roleIds) {
			System.out.println(roleIds);
			groupRoleRepository.deleteByGroupIdAndRoleId(groupId, roleId);
		}
		return 1;
	}


	@Override
	public List<GroupRole> findAllGroupByRole(Long roleId) {
		Optional<Role> roleOptional = roleRepository.findByIdAndNonDel(roleId,true);
		List<GroupRole> list = groupRoleRepository.findByRole(roleOptional.get());
		for (GroupRole groupRole : list) {
			System.out.println(groupRole.getGroup().getName());
		}
		return list;
		
	}


	@Override
	public Boolean existsByRole(long roleId) {
		Optional<Role> exist = roleRepository.findByIdAndNonDel(roleId, false);
		if (!exist.isPresent()) {
			return false;
		}
		return true;
	}


	@Override
	public List<Group> convertGroupRoleToGroup(List<GroupRole> groupRole) {
		List<Group> group = new ArrayList<>();
		for (GroupRole groupRole2 : groupRole) {
			group.add(groupRole2.getGroup());
		}
		return group;
	}


	@Override
	public GroupRole addGroupToRole(Long groupId, Long roleId) {
		return addRoleToGroup(groupId, roleId);
	}


	@Override
	public Boolean deleteGroupFormRole(Long groupId, Long roleId) {
		
		return deleteRoleFormGroup(groupId, roleId);
	}


	@Override
	public Integer deleteListGroupFromRole(Long roleId, List<Long> groupIds) {
		Optional<Role> roleOptional = roleRepository.findByIdAndNonDel(roleId,true);
		if (!roleOptional.isPresent()) {
			return 0;
		}
		for (Long groupId : groupIds) {
			System.out.println(groupId);
			groupRoleRepository.deleteByGroupIdAndRoleId(groupId, roleId);
		}
		return 1;
	}

	

}
