package user.management.vn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	@GetMapping("my-profile")
	public String showUserProfile() {
		return "user-home";
	}
}
