package user.management.vn.service;

import java.util.List;

import user.management.vn.entity.Group;
import user.management.vn.entity.User;
import user.management.vn.entity.UserDTO;
import user.management.vn.entity.UserGroup;
import user.management.vn.entity.UserRole;
import user.management.vn.entity.response.UserResponse;

/**
 * 
 * @summary handle service of user
 * @author Thehap Rok
 *
 */
public interface UserService {


	/**
	 * @summary convert list object UserGroup to list object User 
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param userGroups
	 * @return List<User>
	 */
	List<User> convertUserGroupsToUsers(List<UserGroup> userGroups);
	
	/**
	 * @summary convert list object UserRole to list object User
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param userRoles
	 * @return List<User>
	 */
	List<User> convertUserRolesToUsers(List<UserRole> userRoles);
	
	/**
	 * @summary convert list object User to list object UserResponse
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param listUser
	 * @return List<UserResponse>
	 */
	List<UserResponse> convertUserToUserResponse(List<User> listUser);	
	
	/**
	 * @summary get all user of group from database 
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @return
	 * @return List<UserResponse>
	 */
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
	
	void removeRoleOfGroupFromUserRole(Group group,User user);
	
	UserRole upgradeUserToAdmin(Long userId);
	
	boolean activeUser(Long id);
	
	/**
	 * 
	* @summary Save User
	* @date Aug 21, 2018
	* @author Tai
	* @param user
	* @return
	* @return User
	 */
	User saveUser(User user);
	
	
	

}
