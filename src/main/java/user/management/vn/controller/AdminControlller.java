package user.management.vn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminControlller {
	
	@GetMapping("/groups/{groupId}/users")
	public String showUserInGroup(@PathVariable("groupId")Long groupId,Model model) {
		model.addAttribute("groupId", groupId);
		return "user-in-group";
	}
	
	@GetMapping("/roles/sys")
	public String showRoleSystem() {
		return "role-sys";
	}
	
	@GetMapping("/roles/group")
	public String showRoleGroup() {
		return "role-group";
	}
}
