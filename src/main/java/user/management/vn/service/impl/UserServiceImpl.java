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
import user.management.vn.entity.UserDetail;
import user.management.vn.entity.UserGroup;
import user.management.vn.entity.UserRole;
import user.management.vn.entity.dto.UserDTO;
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

	/**
	 * @summary get all user of group from database
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @return
	 * @return List<UserResponse>
	 */
	@Override
	public List<UserResponse> getAllUserOfGroup(Long groupId) {
		List<UserGroup> userGroups = UserGroupRepository.findAllUserOfGroupId(groupId);
		List<User> listUser = convertUserGroupsToUsers(userGroups);
		return convertUserToUserResponse(listUser);
	}

	/**
	 * @summary convert list object UserGroup to list object User
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param userGroups
	 * @return List<User>
	 */
	@Override
	public List<User> convertUserGroupsToUsers(List<UserGroup> userGroups) {
		List<User> listUser = new ArrayList<User>();
		for (UserGroup userGroup : userGroups) {
			listUser.add(userGroup.getUser());
		}
		return listUser;
	}

	/**
	 * @summary convert list object UserRole to list object User
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param userRoles
	 * @return List<User>
	 */
	@Override
	public List<User> convertUserRolesToUsers(List<UserRole> userRoles) {
		List<User> listUser = new ArrayList<User>();
		for (UserRole userRole : userRoles) {
			listUser.add(userRole.getUser());
		}
		return listUser;
	}

	/**
	 * @summary convert list object User to list object UserResponse
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param listUser
	 * @return List<UserResponse>
	 */
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

	/**
	 * @summary add one User into database
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param userDTO
	 * @return User
	 */
	@Override
	public User addUser(UserDTO userDTO) {
		User user = this.convertUserDtoToUser(userDTO);
		System.out.println(user.getEmail() + ", " + user.getPassword());
		return userRepository.save(user);
	}

	/**
	 * @summary update infomation of a User based on id of user
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param userDTO
	 * @return
	 * @return User
	 */
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

	/**
	 * @summary delete User from database based on id of user
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param id
	 * @return boolean
	 */
	@Transactional
	@Override
	public boolean deleteUserById(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (!optionalUser.isPresent()) {
			return false;
		}
		userRepository.deleteUser(id);
		deleteUserGroup(id);
		deleteAllRoleOfUser(id);
		return true;
	}

	/**
	 * @summary return list of all user from database
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @return List<UserResponse>
	 */
	@Override
	public List<UserResponse> getAllUsers() {
		List<User> listUser = userRepository.findAll();
		return convertUserToUserResponse(listUser);
	}
	
	public List<User> findAllUser() {
		return userRepository.findAll();
	}
	
	/**
	 * @summary find a user base on id of user, return object of type UserResponse
	 *          class
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param userId
	 * @return UserResponse
	 */
	public UserResponse findUserById(Long userId) {
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			return null;
		}
		UserResponse userResponse = new UserResponse();
		userResponse.addPropertiesFromUser(user.get());
		return userResponse;
	}

	/**
	 * @summary check whether duplicate email existed of a user in database
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param email
	 * @return boolean
	 */
	@Override
	public boolean checkDuplicateEmail(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isPresent())
			return true;
		return false;
	}

	/**
	 * @summary find a user base on id of user, return object of type User class
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param userID
	 * @return User
	 */
	@Override
	public User findUserByUserId(Long userID) {
		Optional<User> optionalUser = userRepository.findById(userID);
		if (!optionalUser.isPresent()) {
			return null;
		}
		return optionalUser.get();
	}

	/**
	 * @summary convert UserDTO object to User object
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param userDTO
	 * @return User
	 */
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

	/**
	 * @summary return User base email of User
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param email
	 * @return User
	 */
	@Override
	public User getUserByEmail(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (!optionalUser.isPresent()) {
			return null;
		}
		return optionalUser.get();
	}

	/**
	 * @summary remove all role of group when reomve user
	 * @date Aug 23, 2018
	 * @author Thehap Rok
	 * @param group
	 * @param user
	 * @return void
	 */
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

	/**
	 * @summary upgrate role user to admin
	 * @date Aug 23, 2018
	 * @author Thehap Rok
	 * @param userId
	 * @return UserRole
	 */
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
	
	/**
	 * @summary active User
	 * @date Aug 22, 2018
	 * @author ThaiLe
	 * @param id
	 * @return boolean
	 */
	@Transactional
	@Override
	public boolean activeUser(Long id) {
		Optional<User> optional = userRepository.findById(id);
		if(!optional.isPresent()) {
			return false;
		}
		userRepository.activeUser(id);
		return true;
	}
  
	/**
	 * @summary Save User
	 * @date Aug 21, 2018
	 * @author Tai
	 * @param user
	 * @return User
	 */
	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	/**
	 * @summary delete all role of user
	 * @date Aug 23, 2018
	 * @author Thehap Rok
	 * @param userId
	 * @return void
	 */
	@Override
	public void deleteAllRoleOfUser(Long userId) {
		userRoleRepository.deleteUserRoleByUserId(userId);
	}

	/**
	 * @summary delete all group of user
	 * @date Aug 23, 2018
	 * @author Thehap Rok
	 * @param userId
	 * @return void
	 */
	@Override
	public void deleteUserGroup(Long userId) {
		UserGroupRepository.deleteUserGroupByUserId(userId);
	}

	/**
	 * @summary edit User
	 * @date Aug 22, 2018
	 * @author ThaiLe
	 * @param objUser
	 * @return User
	 */
	@Override
	public User editUser(User objUser) {
		return userRepository.save(objUser);
	}
	
}
