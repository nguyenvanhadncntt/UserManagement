package user.management.vn.service;

import java.security.Principal;

import org.springframework.stereotype.Service;

import user.management.vn.entity.User;
import user.management.vn.entity.response.UserResponse;

public interface PasswordService {
	UserResponse viewCurrentUserResponse(Principal principal);
	User viewCurrentUsers(Principal principal);
	Boolean checkDuplicatePasswordCurrent(String existingPassword, String dbPassword);
	Boolean checkDuplicateNewPasswords(String newPassword,String existingPassword);
	Boolean checkDuplicateMatchingPassword(String newPassword, String NewMatchingPassword);
	User saveNewPasswords(User user, String newPassword);
}
