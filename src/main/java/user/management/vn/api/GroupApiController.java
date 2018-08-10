package user.management.vn.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import user.management.vn.entity.User;
import user.management.vn.entity.response.ListPaging;
import user.management.vn.service.UserService;

@RestController
@RequestMapping("/api")
public class GroupApiController {

	@Autowired
	private UserService userService;

	@GetMapping(path = "/group/{groupId}/users")
	public ResponseEntity<Object> getListUserInGroup(@PathVariable(name = "groupId") long groupId,
			@RequestParam(name = "index", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(name = "size", required = false, defaultValue = "5") int size,
			@RequestParam(name = "fieldSort", required = false, defaultValue = "null") String fieldSort
			,HttpServletRequest request) {
		System.out.println(groupId);
		List<User> listUser = userService.getAllUserOfGroup(groupId);
		ListPaging<User> listPagging = new ListPaging<>(listUser, size, pageIndex,fieldSort, request);
		return new ResponseEntity<>(listPagging, HttpStatus.OK);
	}
}
