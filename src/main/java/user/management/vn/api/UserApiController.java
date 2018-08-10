package user.management.vn.api;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import user.management.vn.entity.User;
import user.management.vn.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;

	@GetMapping(path = "/group/{groupId}/users")
	public ResponseEntity<Object> getListUserInGroup(@PathVariable(name = "groupId") long groupId) {
		System.out.println(groupId);
		List<User> listUser = userService.getAllUserOfGroup(groupId);
		int sizeOfListUser = listUser.size();
		if (sizeOfListUser == 0) {
			return new ResponseEntity<>(listUser,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(listUser, HttpStatus.OK);
	}
}
