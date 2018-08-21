package user.management.vn.api;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import user.management.vn.entity.Group;
import user.management.vn.entity.GroupRole;
import user.management.vn.entity.Role;
import user.management.vn.entity.User;
import user.management.vn.entity.UserGroup;
import user.management.vn.entity.response.ListPaging;
import user.management.vn.entity.response.UserResponse;
import user.management.vn.exception.GroupNotFoundException;
import user.management.vn.query.GroupQueryCondition;
import user.management.vn.service.GroupService;
import user.management.vn.service.RoleGroupService;
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
	private RoleGroupService roleGroupService;

	@Autowired
	private SearchService searchService;

	/**
	 * @summary api get all user of group base on group id
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param pageIndex
	 * @param size
	 * @param fieldSort
	 * @param request
	 * @return ResponseEntity<Object>
	 */
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

	/**
	 * @summary view list all group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param pageIndex
	 * @param size
	 * @param fieldSort
	 * @param request
	 * @return ResponseEntity<Object>
	 */
	@GetMapping
	public ResponseEntity<Object> getAllGroup(
			@RequestParam(name = "index", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(name = "size", required = false, defaultValue = "5") int size,
			@RequestParam(name = "fieldSort", required = false, defaultValue = "null") String fieldSort,
			HttpServletRequest request) {
		List<Group> listGroup = groupService.viewAllGroup();
		if (listGroup.size() == 0) {
			return new ResponseEntity<>(listGroup, HttpStatus.NOT_FOUND);
		}
		ListPaging<Group> listPagging = new ListPaging<>(listGroup, size, pageIndex, fieldSort, request);
		return new ResponseEntity<>(listPagging, HttpStatus.OK);
	}

	/**
	 * 
	 * @summary get information of group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param id
	 * @param pageIndex
	 * @param size
	 * @param fieldSort
	 * @param request
	 * @return
	 * @return ResponseEntity<Object>
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Object> getGroup(@PathVariable("id") long id,
			@RequestParam(name = "index", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(name = "size", required = false, defaultValue = "5") int size,
			@RequestParam(name = "fieldSort", required = false, defaultValue = "null") String fieldSort,
			HttpServletRequest request) {
		Optional<Group> group = groupService.viewGroup(id);
		if (!group.isPresent()) {
			throw new GroupNotFoundException("Group Not Found id: " + id);
		}
		return new ResponseEntity<>(group, HttpStatus.OK);
	}

	/**
	 * 
	 * @summary create a group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param group
	 * @return
	 * @return ResponseEntity<Object>
	 */
	@PostMapping
	public ResponseEntity<Object> createGroup(@RequestBody Group group) {
		Group savedGroup = groupService.addGroup(group);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedGroup.getId())
				.toUri();
		return new ResponseEntity<>("Cread group successful", HttpStatus.CREATED);
	}

	/**
	 * @summary update a group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param group
	 * @param id
	 * @return ResponseEntity<Object>
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateGroup(@RequestBody Group group, @PathVariable("id") long id) {
		Optional<Group> groupOptional = groupService.viewGroup(id);
		if (!groupOptional.isPresent()) {
			throw new GroupNotFoundException("Group Not Found id: " + id);
		}
		group.setUserGroups(groupOptional.get().getUserGroups());
		group.setGroupRoles(groupOptional.get().getGroupRoles());
		group.setId(id);
		groupService.saveGroup(group);
		return new ResponseEntity<>("Update group successful", HttpStatus.OK);

	}

	/**
	 * @summary delete a group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param id
	 * @return ResponseEntity<Object>
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteGroup(@PathVariable("id") long id) {
		Optional<Group> deletedGroup = groupService.deleteGroup(id);
		if (!deletedGroup.isPresent()) {
			throw new GroupNotFoundException("Group Not Found id: " + id);
		}
		return new ResponseEntity<>("Dedelte group successful", HttpStatus.OK);
	}

	/**
	 * @summary list all roles in a group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param id
	 * @param pageIndex
	 * @param size
	 * @param fieldSort
	 * @param request
	 * @return ResponseEntity<Object>
	 */
	@GetMapping("/{id}/roles")
	public ResponseEntity<Object> viewRolesOfGroup(@PathVariable("id") long id,
			@RequestParam(name = "index", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(name = "size", required = false, defaultValue = "5") int size,
			@RequestParam(name = "fieldSort", required = false, defaultValue = "null") String fieldSort,
			HttpServletRequest request) {
		if (roleGroupService.existsByGroup(id)) {
			throw new GroupNotFoundException("Group Not Found id: " + id);
		}
		List<GroupRole> list = roleGroupService.findAllRoleByGroup(id);
		List<Role> listRole = roleGroupService.convertGroupRoleToRole(list);
		if (list.isEmpty()) {
			return new ResponseEntity<>("no role", HttpStatus.NOT_FOUND);
		}
		ListPaging<Role> listPagging = new ListPaging<>(listRole, size, pageIndex, fieldSort, request);
		return new ResponseEntity<>(listPagging, HttpStatus.OK);
	}

	/**
	 * @summary add role in group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param groupId
	 * @param roleId
	 * @return ResponseEntity<Object>
	 */
	@PostMapping("/{groupId}/roles/{roleId}")
	public ResponseEntity<Object> creadRoleGroup(@PathVariable("groupId") long groupId,
			@PathVariable("roleId") long roleId) {
		GroupRole groupRole = roleGroupService.addRoleToGroup(groupId, roleId);
		if (groupRole == null) {
			return new ResponseEntity<>("role da co trong group", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("add role successful", HttpStatus.CREATED);
	}

	/**
	 * @summary api add new user to group base on user id and group id
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param userId
	 * @return ResponseEntity<Object>
	 */
	@PostMapping(path = "/{groupId}/users/{userId}")
	public ResponseEntity<Object> addUserToGroup(@PathVariable(name = "groupId") Long groupId,
			@PathVariable(name = "userId") Long userId) {
		UserGroup userGroup = groupService.addNewUserToGroup(groupId, userId);
		if (userGroup == null) {
			return new ResponseEntity<>("user or group not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Add user successful", HttpStatus.CREATED);
	}

	/**
	 * @summary Api delete role form group
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param groupId
	 * @param roleId
	 * @return ResponseEntity<Object>
	 */
	@DeleteMapping("/{groupId}/roles/{roleId}")
	public ResponseEntity<Object> removeRoleFromGroup(@PathVariable("groupId") long groupId,
			@PathVariable("roleId") long roleId) {
		boolean deleted = roleGroupService.deleteRoleFormGroup(groupId, roleId);
		if (!deleted) {
			return new ResponseEntity<>("Group not found", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("delete successful", HttpStatus.OK);
	}

	/**
	 * @summary api remove many user in group
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param listIdWapper
	 * @return ResponseEntity<Object>
	 */
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

	/**
	 * @summary api remove one user in group
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param userId
	 * @return
	 * @return ResponseEntity<Object>
	 */
	@DeleteMapping(path = "/{groupId}/users/{userId}")
	public ResponseEntity<Object> removeUserFromGroup(@PathVariable("groupId") Long groupId,
			@PathVariable("userId") Long userId) {
		Integer result = groupService.removeUserFromGroup(groupId, userId);
		if (result == 0) {
			return new ResponseEntity<>("Group not found", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("delete successful", HttpStatus.OK);
	}

	/**
	 * @summary api find user not in group by fullname of email
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param searchParam
	 * @return ResponseEntity<Object>
	 */
	@GetMapping(path = "/{groupId}/user/search-not-in")
	public ResponseEntity<Object> findUserNotInGroupByNameOrEmail(@PathVariable("groupId") Long groupId,
			@RequestParam("searchParam") String searchParam) {
		List<UserResponse> users = groupService.findUserNotInGroupByNameOrEmail(groupId, searchParam);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	/**
	 * @summary api search user in group base on any field
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param searchValue
	 * @param fieldSearch
	 * @param pageIndex
	 * @param size
	 * @param fieldSort
	 * @param request
	 * @return ResponseEntity<Object>
	 */
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

	/**
	 * @summary api get infor of user in group base on group id and user id
	 * @date Aug 16, 2018
	 * @author Thehap Rok
	 * @param groupId
	 * @param userId
	 * @return ResponseEntity<Object>
	 */
	@GetMapping(path = "/{groupId}/users/{userId}")
	public ResponseEntity<Object> getInforUserInGroup(@PathVariable(name = "groupId") Long groupId,
			@PathVariable(name = "userId") Long userId) {
		UserResponse user = groupService.getInforOfUser(groupId, userId);
		if (user == null) {
			return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

}
