package user.management.vn.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import user.management.vn.entity.User;

import user.management.vn.entity.UserGroup;
import user.management.vn.entity.UserRole;
import user.management.vn.entity.response.UserResponse;
import user.management.vn.repository.UserGroupRepository;
import user.management.vn.repository.UserRepository;
import user.management.vn.service.UserService;
import user.management.vn.entity.UserDTO;
import user.management.vn.entity.UserDetail;


@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserGroupRepository UserGroupRepository;
	
	@Autowired
	private UserRepository userRepository;
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
		System.out.println(user.getEmail() + ", "+ user.getPassword());
		return userRepository.save(user);		
	}

	@Override
	public User updateUser(UserDTO userDTO) {
		User oldUser = this.getUserByEmail(userDTO.getEmail());
		System.out.println("Old user: " +  oldUser.getEmail() + ", "+  oldUser.getPassword());
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
		if(!optionalUser.isPresent()) {
			return false;
		}
		userRepository.deleteUser(id);
		return true;
	}

	@Override
	public List<UserResponse> getAllUsers() {
		// TODO Auto-generated method stub
		List<User> listUser =  userRepository.findAll();
		return convertUserToUserResponse(listUser);
	}

	@Override
	public List<UserResponse> getUsersByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserResponse findUserById(Long userId) {
		// TODO Auto-generated method stub
		Optional<User> user =  userRepository.findById(userId);
		if(!user.isPresent()) {
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
		if(optionalUser.isPresent()) return true;
		return false;
	}

	@Override
	public User findUserByUserId(Long userID) {
		// TODO Auto-generated method stub
		Optional<User> optionalUser =  userRepository.findById(userID);
		if(!optionalUser.isPresent()) {
			return null;
		}
		return optionalUser.get();
	}

	@Override
	public User convertUserDtoToUser(UserDTO userDTO) {
		User user = new User();
		UserDetail userDetail = new UserDetail(userDTO.getFullname(), userDTO.getPhone(),
				userDTO.getAddress(), userDTO.getGender(), userDTO.getBirthday());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		userDetail.setUser(user);
		user.setUserDetail(userDetail);
		System.out.println("convert: "+user.getEmail() + user.getUserDetail().getFullname());
		return user;
	}
	
	@Override
	public User getUserByEmail(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if(!optionalUser.isPresent()) {
			return null;
		}
		return optionalUser.get();
	}

}
