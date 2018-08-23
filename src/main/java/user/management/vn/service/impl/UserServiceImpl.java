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
import user.management.vn.entity.User;
import user.management.vn.entity.UserDTO;
import user.management.vn.entity.UserDetail;
import user.management.vn.entity.UserGroup;
import user.management.vn.entity.UserRole;
import user.management.vn.entity.response.UserResponse;
import user.management.vn.exception.UserAlreadyAdminException;
import user.management.vn.exception.UserNotFoundException;
import user.management.vn.repository.GroupRoleRepository;
import user.management.vn.repository.RoleRepository;
import user.management.vn.repository.UserGroupRepository;
import user.management.vn.repository.UserRepository;
import user.management.vn.repository.UserRoleRepository;
import user.management.vn.service.UserService;
import user.management.vn.util.RoleSystem;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserGroupRepository UserGroupRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private GroupRoleRepository groupRoleRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<UserResponse> getAllUserOfGroup(Long groupId) {
		List<UserGroup> userGroups = UserGroupRepository.findAllUserOfGroupId(groupId);
		List<User> listUser = convertUserGroupsToUsers(userGroups);
		return convertUserToUserResponse(listUser);
	}

	@Override
	public List<User> convertUserGroupsToUsers(List<UserGroup> userGroups) {
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
	public List<UserResponse> convertUserToUserResponse(List<User> listUser) {
		List<UserResponse> listUserResponse = new ArrayList<>();
		for (User user : listUser) {
			UserResponse userResponse = new UserResponse();
			userResponse.addPropertiesFromUser(user);
			listUserResponse.add(userResponse);
		}
		return listUserResponse;
	}

	@Override
	public User addUser(UserDTO userDTO) {
		User user = this.convertUserDtoToUser(userDTO);
		System.out.println(user.getEmail() + ", " + user.getPassword());
		return userRepository.save(user);
	}

	@Override
	public User updateUser(UserDTO userDTO) {
		User oldUser = this.getUserByEmail(userDTO.getEmail());
		
		oldUser.setPassword(userDTO.getPassword());
		UserDetail oldUserDetail = oldUser.getUserDetail();
		oldUserDetail.setAddress(userDTO.getAddress());
		oldUserDetail.setBirthDay(userDTO.getBirthday());
		oldUserDetail.setFullname(userDTO.getFullname());
		oldUserDetail.setGender(userDTO.getGender());
		oldUserDetail.setPhone(userDTO.getPhone());
		oldUserDetail.setUser(oldUser);
		oldUser.setUserDetail(oldUserDetail);
		return userRepository.save(oldUser);
	}

	@Transactional
	@Override
	public boolean deleteUserById(Long id) {
		// TODO Auto-generated method stub
		Optional<User> optionalUser = userRepository.findById(id);
		if (!optionalUser.isPresent()) {
			return false;
		}
		userRepository.deleteUser(id);
		deleteUserGroup(id);
		deleteAllRoleOfUser(id);
		return true;
	}

	@Override
	public List<UserResponse> getAllUsers() {
		// TODO Auto-generated method stub
		List<User> listUser = userRepository.findAll();
		return convertUserToUserResponse(listUser);
	}
	
	public List<User> findAllUser() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}
	
	public UserResponse findUserById(Long userId) {
		// TODO Auto-generated method stub
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			return null;
		}
		UserResponse userResponse = new UserResponse();
		userResponse.addPropertiesFromUser(user.get());
		return userResponse;
	}

	@Override
	public boolean checkDuplicateEmail(String email) {
		// TODO Auto-generated method stub
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isPresent())
			return true;
		return false;
	}

	@Override
	public User findUserByUserId(Long userID) {
		// TODO Auto-generated method stub
		Optional<User> optionalUser = userRepository.findById(userID);
		if (!optionalUser.isPresent()) {
			return null;
		}
		return optionalUser.get();
	}

	@Override
	public User convertUserDtoToUser(UserDTO userDTO) {
		User user = new User();
		UserDetail userDetail = new UserDetail(userDTO.getFullname(), userDTO.getPhone(), userDTO.getAddress(),
				userDTO.getGender(), userDTO.getBirthday());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		userDetail.setUser(user);
		user.setUserDetail(userDetail);
		System.out.println("convert: " + user.getEmail() + user.getUserDetail().getFullname());
		return user;
	}

	@Override
	public User getUserByEmail(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (!optionalUser.isPresent()) {
			return null;
		}
		return optionalUser.get();
	}

	@Override
	public void removeRoleOfGroupFromUserRole(Group group, User user) {
		List<UserGroup> userGroups = user.getUserGroups();
		List<GroupRole> listGroupRole = group.getGroupRoles();
		for (GroupRole groupRole : listGroupRole) {
			boolean exist = true;
			Long roleId = groupRole.getRole().getId();
			for (UserGroup userGroup : userGroups) {
				Long groupId = userGroup.getGroup().getId();
				Optional<GroupRole> optional = groupRoleRepository.findByGroupIdAndRoleId(groupId, roleId);
				if (!optional.isPresent()) {
					exist = false;
					break;
				}
			}
			if (!exist) {
				userRoleRepository.deleteByUserIdAndRoleId(user.getId(), roleId);
			}
		}
	}

	@Override
	public UserRole upgradeUserToAdmin(Long userId) 
			throws UserNotFoundException, UserAlreadyAdminException {
		Role adminRole = roleRepository.findByRoleName(RoleSystem.ADMIN);
		Optional<User> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("User not found!!!");
		}
		
		User user = userOptional.get();

		Boolean checkExist = userRoleRepository.existsByUserIdAndRoleId(user.getId(), adminRole.getId());
		if (checkExist) {
			throw new UserAlreadyAdminException("User were Admin!!!");
		}
		UserRole userRole = new UserRole(user, adminRole);
		return userRoleRepository.save(userRole);
	}
	
	@Transactional
	@Override
	public boolean activeUser(Long id) {
		// TODO Auto-generated method stub
		Optional<User> optional = userRepository.findById(id);
		if(!optional.isPresent()) {
			return false;
		}
		userRepository.activeUser(id);
		return true;
	}
  
	@Override
	public User saveUser(User user) {
		
		return userRepository.save(user);
	}

	@Override
	public void deleteAllRoleOfUser(Long userId) {
		userRoleRepository.deleteUserRoleByUserId(userId);
	}

	@Override
	public void deleteUserGroup(Long userId) {
		UserGroupRepository.deleteUserGroupByUserId(userId);
	}

	@Override
	public User editUser(User objUser) {
		// TODO Auto-generated method stub
		return userRepository.save(objUser);
	}
	
}
