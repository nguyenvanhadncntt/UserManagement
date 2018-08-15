package user.management.vn.service;

import java.util.List;

import user.management.vn.entity.User;
import user.management.vn.entity.UserDTO;
import user.management.vn.entity.UserGroup;
import user.management.vn.entity.UserRole;
import user.management.vn.entity.response.UserResponse;

public interface UserService {

	/*
	 * ha
	 */
	List<User> convertUserGroupsToUsers(List<UserGroup> userGroups);
	List<User> convertUserRolesToUsers(List<UserRole> userRoles);
	List<UserResponse> convertUserToUserResponse(List<User> listUser);	
	List<UserResponse> getAllUserOfGroup(Long groupId);
   
	/**
	* @summary add one User into database
	* @date Aug 15, 2018
	* @author ThaiLe
	* @param userDTO
	* @return User
	 */
	User addUser(UserDTO userDTO);
	
	/**
	* @summary update infomation of a User based on id of user
	* @date Aug 15, 2018
	* @author ThaiLe
	* @param userDTO
	* @return
	* @return User
	 */
	User updateUser(UserDTO userDTO);
	
	/**
	* @summary delete User from database  based on id of user
	* @date Aug 15, 2018
	* @author ThaiLe
	* @param id
	* @return boolean
	 */
	boolean deleteUserById(Long id);
	
	/**
	* @summary return list of all user from database
	* @date Aug 15, 2018
	* @author ThaiLe
	* @return List<UserResponse>
	 */
	List<UserResponse> getAllUsers();
	
	/**
	* @summary get common information of a user
	* @date Aug 15, 2018
	* @author ThaiLe
	* @param name
	* @return List<UserResponse>
	 */
	List<UserResponse> getUsersByName(String name);
	
	/**
	* @summary find a user base on id of user, return object of type UserResponse class
	* @date Aug 15, 2018
	* @author ThaiLe
	* @param userId
	* @return UserResponse
	 */
	UserResponse findUserById(Long userId);
	
	/**
	* @summary find a user base on id of user, return object of type User class
	* @date Aug 15, 2018
	* @author ThaiLe
	* @param userID
	* @return User
	 */
	User findUserByUserId(Long userID);
	
	/**
	* @summary check whether duplicate email existed of a user in database
	* @date Aug 15, 2018
	* @author ThaiLe
	* @param email
	* @return boolean
	 */
	boolean checkDuplicateEmail(String email);
	
	/**
	* @summary convert UserDTO object to User object
	* @date Aug 15, 2018
	* @author ThaiLe
	* @param userDTO
	* @return User
	 */
	User convertUserDtoToUser(UserDTO userDTO);
	
	/**
	* @summary return User base email of User
	* @date Aug 15, 2018
	* @author ThaiLe
	* @param email
	* @return User
	 */
	User getUserByEmail(String email);

}
