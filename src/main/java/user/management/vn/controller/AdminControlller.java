package user.management.vn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminControlller {
	
	@GetMapping("/groups")
	public String viewGroup() {
		return "admin-group";
	} 

	@GetMapping("/groups/{groupId}/users")
	public String showUserInGroup(@PathVariable("groupId")Long groupId,Model model) {
		model.addAttribute("groupId", groupId);
		return "user-in-group";
	}
	
	@GetMapping("/roles/sys")
	public String showRoleSystem() {
		return "role-sys";
	}
	

	@GetMapping("/user/show-all")
	public String showAllUser() {
		return "user";
	}
	
	@GetMapping("/user/edit/{userId}")
	public String editUser(@PathVariable Long userId, Model model) {
		model.addAttribute("id_user", userId);
		return "edit-user";
	}
	
	@GetMapping("/user/view/{userId}")
	public String viewUser(@PathVariable Long userId, Model model) {
		model.addAttribute("id_user", userId);
		return "view-user";
	}	

	@GetMapping("/roles/group")
	public String showRoleGroup() {
		return "role-group";
	}

}
