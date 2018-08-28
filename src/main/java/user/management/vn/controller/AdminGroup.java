package user.management.vn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminGroup {

	@GetMapping("/admin-group")
	public String viewGroup() {
		return "admin-group";
	} 
}
