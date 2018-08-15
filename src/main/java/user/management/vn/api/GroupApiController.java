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

import user.management.vn.entity.User;
import user.management.vn.entity.UserGroup;
import user.management.vn.entity.response.ListPaging;
import user.management.vn.entity.response.UserResponse;
import user.management.vn.query.GroupQueryCondition;
import user.management.vn.service.GroupService;
import user.management.vn.service.SearchService;
import user.management.vn.service.UserService;
import user.management.vn.util.EntityName;
import user.management.vn.wrapper.ListIdWrapper;

@RestController
@RequestMapping("/api/groups")
public class GroupApiController {

	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private SearchService searchService;

	@GetMapping(path = "/{groupId}/users")
	public ResponseEntity<Object> getListUserInGroup(@PathVariable(name = "groupId") Long groupId,
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
	public ResponseEntity<Object> addUserToGroup(@PathVariable(name = "groupId") Long groupId,
			@PathVariable(name = "userId") Long userId) {
		UserGroup userGroup = groupService.addNewUserToGroup(groupId, userId);
		if (userGroup == null) {
			return new ResponseEntity<>("user or group not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Add user successful", HttpStatus.CREATED);
	}

	@DeleteMapping(path = "/{groupId}/users")
	public ResponseEntity<Object> removeListUserFromGroup(@PathVariable("groupId") Long groupId,
			@RequestBody ListIdWrapper listIdWapper) {
		List<Long> userIds = listIdWapper.getIds();
		Integer result = groupService.removeListUseFromGroup(groupId, userIds);
		if (result == 0) {
			return new ResponseEntity<>("Group not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("delete successful", HttpStatus.OK);
	}

	@DeleteMapping(path = "/{groupId}/users/{userId}")
	public ResponseEntity<Object> removeUserFromGroup(@PathVariable("groupId") Long groupId,
			@PathVariable("userId") Long userId) {
		Integer result = groupService.removeUserFromGroup(groupId, userId);
		if (result == 0) {
			return new ResponseEntity<>("Group not found", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("delete successful", HttpStatus.OK);
	}

	@GetMapping(path = "/{groupId}/user/search-not-in")
	public ResponseEntity<Object> findUserNotInGroupByNameOrEmail(@PathVariable("groupId") Long groupId,
			@RequestParam("searchParam") String searchParam) {
		List<UserResponse> users = groupService.findUserNotInGroupByNameOrEmail(groupId, searchParam);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping(path = "/{groupId}/users/search")
	public ResponseEntity<Object> findUserInGroup(@PathVariable(name = "groupId") Long groupId,
			@RequestParam(name = "searchValue") String searchValue,
			@RequestParam(name = "searchField") String fieldSearch,
			@RequestParam(name = "index", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(name = "size", required = false, defaultValue = "5") int size,
			@RequestParam(name = "fieldSort", required = false, defaultValue = "null") String fieldSort,
			HttpServletRequest request) {
		List<User> users = searchService.search(EntityName.USER, fieldSearch, searchValue,
				GroupQueryCondition.conditionSearchUserInGroup(groupId));
		List<UserResponse> listUser = userService.convertUserToUserResponse(users);
		if (listUser.size() == 0) {
			return new ResponseEntity<>(listUser, HttpStatus.NOT_FOUND);
		}
		ListPaging<UserResponse> listPagging = new ListPaging<>(listUser, size, pageIndex, fieldSort, request);
		return new ResponseEntity<>(listPagging, HttpStatus.OK);
	}

	@GetMapping(path = "/{groupId}/users/{userId}")
	public ResponseEntity<Object> getInforUserInGroup(@PathVariable(name = "groupId") Long groupId,
			@PathVariable(name = "userId") Long userId) {
		UserResponse user = groupService.getInforOfUser(groupId, userId);
		if (user == null) {
			return new ResponseEntity<>("user not found",HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
}
