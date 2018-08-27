package user.management.vn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminControlller {
	
	@GetMapping("/group/{groupId}")
	public String showUserInGroup() {
		return "group";
	}
}
