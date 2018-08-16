package user.management.vn.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import user.management.vn.entity.User;
import user.management.vn.entity.UserDTO;
import user.management.vn.entity.response.ListPaging;
import user.management.vn.entity.response.UserResponse;
import user.management.vn.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserApiController {

	@Autowired
	private UserService userService;

	/**
	 * 
	 * @summary api get all user from database
	 * @date Aug 13, 2018
	 * @author ThaiLe
	 * @param pageIndex
	 * @param size
	 * @param fieldSort
	 * @param request
	 * @return ResponseEntity<Object>
	 */
	@GetMapping
	public ResponseEntity<Object> getAllUser(
			@RequestParam(name = "index", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(name = "size", required = false, defaultValue = "5") int size,
			@RequestParam(name = "fieldSort", required = false, defaultValue = "null") String fieldSort,
			HttpServletRequest request) {
		List<UserResponse> listUser = userService.getAllUsers();
		if (listUser.size() == 0) {
			return new ResponseEntity<>(listUser, HttpStatus.NOT_FOUND);
		}
		ListPaging<UserResponse> listPaging = new ListPaging<>(listUser, size, pageIndex, fieldSort, request);
		return new ResponseEntity<>(listPaging, HttpStatus.OK);
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
		System.out.println(userDTO.getPhone() + ", " + userDTO.getPassword() + ", " + userDTO.getEmail());
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

}
