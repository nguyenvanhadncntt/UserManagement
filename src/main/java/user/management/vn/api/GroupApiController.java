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
import user.management.vn.entity.UserGroup;
import user.management.vn.entity.response.ListPaging;
import user.management.vn.entity.response.UserResponse;
import user.management.vn.exception.GroupNotFoundException;
import user.management.vn.service.GroupService;
import user.management.vn.service.RoleGroupService;
import user.management.vn.service.UserService;

@RestController
@RequestMapping("/api/groups")
public class GroupApiController {

	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private RoleGroupService roleGroupService;

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
			return new ResponseEntity<>("user or group not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Add user successful", HttpStatus.CREATED);
	}


	/**
	 * 
	* @summary view list all group 
	* @date Aug 16, 2018
	* @author Tai
	* @param pageIndex
	* @param size
	* @param fieldSort
	* @param request
	* @return
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
	 * 
	* @summary update a group
	* @date Aug 16, 2018
	* @author Tai
	* @param group
	* @param id
	* @return
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
	 * 
	* @summary delete a group
	* @date Aug 16, 2018
	* @author Tai
	* @param id
	* @return
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
	 * 
	* @summary list all roles in a group
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
	 * 
	* @summary add role in group
	* @date Aug 16, 2018
	* @author Tai
	* @param groupId
	* @param roleId
	* @return
	* @return ResponseEntity<Object>
	 */
	@PostMapping("/{groupId}/roles/{roleId}")
	public ResponseEntity<Object> creadRoleGroup(@PathVariable("groupId") long groupId,
			@PathVariable("roleId") long roleId) {
		GroupRole groupRole = roleGroupService.addRoleToGroup(groupId, roleId);
		if (groupRole == null) {
			return new ResponseEntity<>("role da co trong group", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Add user successful", HttpStatus.CREATED);
	}
	

	/**
	 * 
	* @summary Api delete role form group
	* @date Aug 16, 2018
	* @author Tai
	* @param groupId
	* @param roleId
	* @return
	* @return ResponseEntity<Object>
	 */
	@DeleteMapping("/{groupId}/roles/{roleId}")
	public ResponseEntity<Object> removeRoleFromGroup(@PathVariable("groupId") long groupId,
			@PathVariable("roleId") long roleId) {
		boolean deleted = roleGroupService.deleteRoleFormGroup(groupId, roleId);
		if (!deleted) {
			return new ResponseEntity<>("Group not found", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("delete successful",HttpStatus.OK);
	}
}
