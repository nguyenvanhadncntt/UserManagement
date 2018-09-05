package user.management.vn.api;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import user.management.vn.entity.User;
import user.management.vn.entity.dto.PasswordDTO;
import user.management.vn.entity.response.UserResponse;
import user.management.vn.service.PasswordService;
import user.management.vn.service.UserService;

@RestController
@RequestMapping("/my-profile")
public class UserProfileApiController {
	@Autowired
	UserService userService;

	@Autowired
	PasswordService passwordService;

	@GetMapping
	public ResponseEntity<Object> viewMyProfile(Principal principal) {
		UserResponse userRespone = passwordService.viewCurrentUserResponse(principal);
		
		return new ResponseEntity<>(userRespone, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<Object> updateMyProfile(@Valid @RequestBody UserResponse userResponse, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String fieldName = bindingResult.getFieldError().getField();
			String message = bindingResult.getFieldError().getDefaultMessage();
			return new ResponseEntity<>(fieldName+" : "+message,HttpStatus.BAD_REQUEST);
		}
		if (userResponse == null) {
			return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
		}
		
		userService.saveUser(userResponse);
		return new ResponseEntity<>("Update user successfully", HttpStatus.OK);

	}

	@PutMapping("change-password")
	public ResponseEntity<Object> changePasswordMyProfile(@Valid @RequestBody PasswordDTO passwordDTO, BindingResult bindingResult,
			Principal principal) {
		
		if (passwordDTO == null) {
			return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
		}
		if (bindingResult.hasErrors()) {
			String fieldName = bindingResult.getFieldError().getField();
			String message = bindingResult.getFieldError().getDefaultMessage();
			return new ResponseEntity<>(fieldName+" : "+message,HttpStatus.BAD_REQUEST);
		}
		User user = passwordService.viewCurrentUsers(principal);

		String existingPassword = passwordDTO.getPasswordCurrent();
		String dbPassword = user.getPassword();
		String newPassword = passwordDTO.getNewPassword();
		String newMatchingPassword = passwordDTO.getNewMatchingPassword();

		boolean checkDuplicatePasswordCurrent = passwordService.checkDuplicatePasswordCurrent(existingPassword,
				dbPassword);
		boolean checkDuplicateNewPassowrds = passwordService.checkDuplicateNewPasswords(newPassword, existingPassword);
		boolean checkDuplicateMatchingPassword = passwordService.checkDuplicateMatchingPassword(newPassword,
				newMatchingPassword);

		if (!checkDuplicatePasswordCurrent) {
			return new ResponseEntity<>("The password you enter does not match the current password",
					HttpStatus.CONFLICT);
		}
		if (checkDuplicateNewPassowrds) {
			System.out.println(checkDuplicateNewPassowrds);
			return new ResponseEntity<>("The password you entered is the same as your current password",
					HttpStatus.CONFLICT);
		}
		if (!checkDuplicateMatchingPassword) {
			return new ResponseEntity<>("The password you entered does not match Password matching",
					HttpStatus.CONFLICT);
		}

		passwordService.saveNewPasswords(user, newPassword);
		return new ResponseEntity<>("Update password", HttpStatus.OK);

	}

	public UserResponse viewCurrentUsers(Principal principal) {
		String email = principal.getName();
		User user = userService.getUserByEmail(email);
		UserResponse userRespone = new UserResponse();
		userRespone.addPropertiesFromUser(user);
		return userRespone;
	}

}
