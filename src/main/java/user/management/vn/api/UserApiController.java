package user.management.vn.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import user.management.vn.entity.User;
import user.management.vn.entity.UserRole;
import user.management.vn.entity.dto.UserDTO;
import user.management.vn.entity.response.UserResponse;
import user.management.vn.exception.UserAlreadyAdminException;
import user.management.vn.exception.UserNotFoundException;
import user.management.vn.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserApiController {

	@Autowired
	private UserService userService;

	/**
	 * @summary api get all user from database
	 * @date Aug 13, 2018
	 * @author ThaiLe
	 * @return ResponseEntity<Object>
	 */
	@GetMapping
	public ResponseEntity<Object> getAllUser() {
		List<UserResponse> listUser = userService.getAllUsers();
		if (listUser.size() == 0) {
			return new ResponseEntity<>(listUser, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(listUser, HttpStatus.OK);

	}

	/**
	 * 
	 * @summary api get an user from id of user
	 * @date Aug 13, 2018
	 * @author ThaiLe
	 * @param id
	 * @return UserResponse
	 */
	@GetMapping(path = "/{id}")
	public UserResponse getUserById(@PathVariable("id") long id) {
		UserResponse userResponse = userService.findUserById(id);
		if (userResponse == null) {
			ResponseEntity.notFound().build();
		}
		return userResponse;
	}

	/**
	 * @summary api delete a user from database base on id of user
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param id
	 * @return ResponseEntity<Object>
	 */
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Object> removeUserById(@PathVariable("id") long id) {
		boolean rs = userService.deleteUserById(id);
		if (!rs) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Delete user successfully", HttpStatus.OK);
	}

	/**
	 * @summary api add a user into database
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param userDTO
	 * @return ResponseEntity<String>
	 */
	@PostMapping
	public ResponseEntity<String> createNewUser(@Valid @RequestBody UserDTO userDTO) {
		if (userService.checkDuplicateEmail(userDTO.getEmail())) {
			return new ResponseEntity<String>("Email is existed", HttpStatus.CONFLICT);
		}
		userService.addUser(userDTO);
		return new ResponseEntity<>("Created user successfully", HttpStatus.OK);
	}

	/**
	 * @summary api edit a user from database
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param userDTO
	 * @return ResponseEntity<String>
	 */
	@PutMapping
	public ResponseEntity<String> editUser(@Valid @RequestBody UserDTO userDTO) {
		User oldUser = userService.getUserByEmail(userDTO.getEmail());
		if (oldUser == null) {
			return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
		}

		System.out.println(userDTO.getPhone() + ", " + userDTO.getPassword() + ", " + userDTO.getEmail());
		userService.updateUser(userDTO);
		return new ResponseEntity<>("Edit user successfully", HttpStatus.OK);
	}

	/**
	 * @summary user register
	 * @date Aug 23, 2018
	 * @author Thehap Rok
	 * @param userDTO
	 * @param rs
	 * @return ResponseEntity<String>
	 */
	@PostMapping(path = "/registration")
	public ResponseEntity<String> registerUserAccount(@Valid @RequestBody UserDTO userDTO, BindingResult rs) {
		if (rs.hasErrors()) {
			System.out.println(rs.getAllErrors().toString());
			return new ResponseEntity<String>("You must complete all infor", HttpStatus.BAD_REQUEST);
		}
		if (userService.checkDuplicateEmail(userDTO.getEmail())) {
			return new ResponseEntity<String>("Email is existed", HttpStatus.CONFLICT);
		}
		System.out.println(userDTO.getPhone() + ", " + userDTO.getPassword() + ", " + userDTO.getEmail());
		userService.addUser(userDTO);
		return new ResponseEntity<>("Created user successfully", HttpStatus.OK);
	}

	/**
	 * @summary upgrade user to admin
	 * @date Aug 17, 2018
	 * @author Thehap Rok
	 * @param userId
	 * @return ResponseEntity<Object>
	 */
	@PutMapping("upgrade-to-admin/{userId}")
	public ResponseEntity<Object> upgradeUserToAdmin(@PathVariable("userId") Long userId) {
		UserRole upgradeRole = null;
		try {
			upgradeRole = userService.upgradeUserToAdmin(userId);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (UserAlreadyAdminException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(upgradeRole, HttpStatus.OK);
	}

}
