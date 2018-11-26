package user.management.vn.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import user.management.vn.ApplicationContextHolder;
import user.management.vn.entity.User;
import user.management.vn.entity.UserDetail;
import user.management.vn.service.UserService;

public class UserCurrent {
	
	@Autowired
	private static UserService userService = ApplicationContextHolder.getBean(UserService.class);

	public static User userCurrent() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userService.getUserByEmail(email);
	}
	
	public static Long getId() {
		User user = userCurrent();
		return user.getId();
	}
	
	public static UserDetail getUserDetail() {
		User user = userCurrent();
		return user.getUserDetail();
	}
}
