package user.management.vn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminControlller {
	
	@GetMapping("/groups")
	public String viewGroup() {
		return "admin-group";
	} 
//	@GetMapping("/groups/{groupId}/roles")
//	public String viewRolesInGroup(@PathVariable("groupId") String groupid, ModelMap model) {
//		return "admin-group-role";
//	}
	
}
