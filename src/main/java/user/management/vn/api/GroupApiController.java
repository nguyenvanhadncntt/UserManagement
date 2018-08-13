package user.management.vn.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import user.management.vn.entity.UserGroup;
import user.management.vn.entity.dto.ListIdWrapper;
import user.management.vn.entity.response.ListPaging;
import user.management.vn.entity.response.UserResponse;
import user.management.vn.service.GroupService;
import user.management.vn.service.UserService;

@RestController
@RequestMapping("/api/groups")
public class GroupApiController {

	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;

	@GetMapping(path = "/{groupId}/users")
	public ResponseEntity<Object> getListUserInGroup(@PathVariable(name = "groupId") long groupId,
			@RequestParam(name = "index", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(name = "size", required = false, defaultValue = "5") int size,
			@RequestParam(name = "fieldSort", required = false, defaultValue = "null") String fieldSort,
			HttpServletRequest request) {
		List<UserResponse> listUser = userService.getAllUserOfGroup(groupId);
		if (listUser.size() == 0) {
			return new ResponseEntity<>(listUser, HttpStatus.NOT_FOUND);
		}
		ListPaging<UserResponse> listPagging = new ListPaging<>(listUser, size, pageIndex, fieldSort, request);
		return new ResponseEntity<>(listPagging, HttpStatus.OK);
	}

	@PostMapping(path = "/{groupId}/users/{userId}")
	public ResponseEntity<Object> addUserToGroup(@PathVariable(name = "groupId") long groupId,
			@PathVariable(name = "userId") long userId) {
		UserGroup userGroup = groupService.addNewUserToGroup(groupId, userId);
		if (userGroup == null) {
			return new ResponseEntity<>("user or group not found", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Add user successful", HttpStatus.CREATED);
	}

	@DeleteMapping(path = "/{groupId}/users")
	public ResponseEntity<Object> removeListUserFromGroup(@PathVariable("groupId") Long groupId,
			@RequestBody ListIdWrapper listIdWapper) {
		List<Long> userIds = listIdWapper.getIds();
		Integer result = groupService.removeListUseFromGroup(groupId, userIds);
		if (result == 0) {
			return new ResponseEntity<>("Group not found", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("delete successful", HttpStatus.OK);
	}

	@DeleteMapping(path="/{groupId}/users/{userId}")
	public ResponseEntity<Object> removeUserFromGroup(@PathVariable("groupId") Long groupId
			,@PathVariable("userId") Long userId) {
		Integer result = groupService.removeUserFromGroup(groupId, userId);
		if (result == 0) {
			return new ResponseEntity<>("Group not found", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("delete successful", HttpStatus.OK);
	}
}
