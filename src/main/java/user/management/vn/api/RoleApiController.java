package user.management.vn.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import user.management.vn.entity.Group;
import user.management.vn.entity.GroupRole;
import user.management.vn.entity.Role;
import user.management.vn.entity.response.ListPaging;
import user.management.vn.exception.RoleNotFoundException;
import user.management.vn.query.RoleQueryCondition;
import user.management.vn.service.RoleGroupService;
import user.management.vn.service.RoleService;
import user.management.vn.service.SearchService;
import user.management.vn.util.EntityName;
import user.management.vn.wrapper.ListIdWrapper;
import user.management.vn.util.RoleScope;

@RestController
@RequestMapping("/api/roles")
public class RoleApiController {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleGroupService roleGroupService;
	
	@Autowired
	private SearchService searchService;

	/**
	 * @summary api get all role from database
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param pageIndex
	 * @param size
	 * @param fieldSort
	 * @param request
	 * @return ResponseEntity<Object>
	 */
	@GetMapping
	public ResponseEntity<Object> getAllRole(
			@RequestParam(name = "index", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(name = "size", required = false, defaultValue = "5") int size,
			@RequestParam(name = "fieldSort", required = false, defaultValue = "null") String fieldSort,
			HttpServletRequest request) {
		List<Role> listRole = roleService.getAllRoles();
		if (listRole.size() == 0) {
			return new ResponseEntity<>(listRole, HttpStatus.NOT_FOUND);
		}
		ListPaging<Role> listPaging = new ListPaging<>(listRole, size, pageIndex, fieldSort, request);
		return new ResponseEntity<>(listPaging, HttpStatus.OK);
	}

	@GetMapping("/sys")
	public ResponseEntity<Object> getAllRoleScopeGroup() {
		List<Role> listRole = roleService.getListRoleByScope(RoleScope.SYSTEM);
		if(listRole.size() == 0) {
			return new ResponseEntity<>("No data",HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listRole,HttpStatus.OK);
	}
	
	@GetMapping("/group")
	public ResponseEntity<Object> getAllRoleScopeSystem() {
		List<Role> listRole = roleService.getListRoleByScope(RoleScope.GROUP);
		if(listRole.size() == 0) {
			return new ResponseEntity<>("No data",HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listRole,HttpStatus.OK);
	}
	
	/**
	 * @summary api get 1 role from database base on id of role
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param id
	 * @return ResponseEntity<Object>
	 */
	@GetMapping(path = "/{id}")
	public ResponseEntity<Object> getRoleById(@PathVariable("id") long id) {
		Role objRole = roleService.getRoleById(id);
		if (objRole == null) {
			return new ResponseEntity<>("Not found role", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(objRole, HttpStatus.OK);
	}

	/**
	 * @summary delete a role from database base on id of role
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param id
	 * @return ResponseEntity<String>
	 */
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<String> deleteRoleById(@PathVariable("id") long id) {
		Role objRole = roleService.getRoleById(id);
		if (objRole == null) {
			return new ResponseEntity<String>("Not found role", HttpStatus.NOT_FOUND);
		}
		roleService.deleteRoleById(id);
		return new ResponseEntity<String>("Delete role successfully", HttpStatus.OK);
	}

	/**
	 * @summary api add a role into database
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param role
	 * @return ResponseEntity<String>
	 */
	@PostMapping
	public ResponseEntity<String> addNewRole(@Valid @RequestBody Role role) {
		Role oldRole = roleService.findByRoleName(role.getRoleName());
		if (oldRole != null) {
			return new ResponseEntity<String>("Role was existed", HttpStatus.CONFLICT);
		}

		try {
			roleService.addRole(role);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return new ResponseEntity<String>("Add role successfully", HttpStatus.OK);
	}

	/**
	 * @summary api edit a role from database
	 * @date Aug 15, 2018
	 * @author ThaiLe
	 * @param role
	 * @return ResponseEntity<String>
	 */
	@PutMapping
	public ResponseEntity<String> editRole(@Valid @RequestBody Role role) {
		Role oldRole = roleService.getRoleById(role.getId());
		if (oldRole == null) {
			return new ResponseEntity<String>("Not found role", HttpStatus.NOT_FOUND);
		}
		Role editRole = null;
		try {
			editRole = roleService.editRole(role);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<String>("Can't edit role", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("Edit role successfully" + editRole.getRoleName(), HttpStatus.NOT_FOUND);
	}
	
	/**
	 * 
	* @summary view all list groups in role
	* @date Aug 20, 2018
	* @author Tai
	* @param id
	* @param pageIndex
	* @param size
	* @param fieldSort
	* @param request
	* @return
	* @return ResponseEntity<Object>
	 */
	@GetMapping("/{id}/groups")
	public ResponseEntity<Object> viewRolesOfGroup(@PathVariable("id") long id,
			@RequestParam(name = "index", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(name = "size", required = false, defaultValue = "5") int size,
			@RequestParam(name = "fieldSort", required = false, defaultValue = "null") String fieldSort,
			HttpServletRequest request) {
		if (roleGroupService.existsByRole(id)) {
			throw new RoleNotFoundException("Role Not Found id: " + id);
		}
		List<GroupRole> list = roleGroupService.findAllGroupByRole(id);
		List<Group> listGroup = roleGroupService.convertGroupRoleToGroup(list);
		if (list.isEmpty()) {
			return new ResponseEntity<>("no role", HttpStatus.NOT_FOUND);
		}
		ListPaging<Group> listPagging = new ListPaging<>(listGroup, size, pageIndex, fieldSort, request);
		return new ResponseEntity<>(listPagging, HttpStatus.OK);
	}

	/**
	 * 
	* @summary add a group in role
	* @date Aug 20, 2018
	* @author Tai
	* @param roleId
	* @param groupId
	* @return
	* @return ResponseEntity<Object>
	 */
	@PostMapping("/{roleId}/groups/{groupId}")
	public ResponseEntity<Object> creadRoleGroup(@PathVariable("roleId") long roleId,
			@PathVariable("groupId") long groupId) {
		GroupRole groupRole = roleGroupService.addGroupToRole(groupId, roleId);
		if (groupRole == null) {
			return new ResponseEntity<>("Group da co trong role", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("add Group successful", HttpStatus.CREATED);
	}


	/**
	 * 
	* @summary delete a group in role
	* @date Aug 20, 2018
	* @author Tai
	* @param groupId
	* @param roleId
	* @return
	* @return ResponseEntity<Object>
	 */
	@DeleteMapping("/{roleId}/groups/{groupId}")
	public ResponseEntity<Object> removeRoleFromGroup(@PathVariable("groupId") long groupId,
			@PathVariable("roleId") long roleId) {
		boolean deleted = roleGroupService.deleteGroupFormRole(groupId, roleId);
		if (!deleted) {
			return new ResponseEntity<>("RoleId not found", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("delete successful", HttpStatus.OK);
	}

	/**
	 * 
	* @summary delete a list group in role
	* @date Aug 20, 2018
	* @author Tai
	* @param roleId
	* @param listIdWapper
	* @return
	* @return ResponseEntity<Object>
	 */
	@DeleteMapping("/{roleId}/groups")
	public ResponseEntity<Object> removeListRoleFromGroup(@PathVariable("roleId") long roleId,
			@RequestBody ListIdWrapper listIdWapper) {
		List<Long> groupIds = listIdWapper.getIds();
		Integer result = roleGroupService.deleteListGroupFromRole(roleId, groupIds);
		if (result == 0) {
			return new ResponseEntity<>("Group not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("delete successful", HttpStatus.OK);

	}
	/**
	 * 
	* @summary search group in role
	* @date Aug 20, 2018
	* @author Tai
	* @param roleId
	* @param searchValue
	* @param fieldSearch
	* @param pageIndex
	* @param size
	* @param fieldSort
	* @param request
	* @return
	* @return ResponseEntity<Object>
	 */
	@GetMapping("/{roleId}/groups/search")
	public ResponseEntity<Object> findRoleInGroup(@PathVariable(name = "roleId") Long roleId,
			@RequestParam(name = "searchValue") String searchValue,
			@RequestParam(name = "searchField") String fieldSearch,
			@RequestParam(name = "index", required = false, defaultValue = "0") int pageIndex,
			@RequestParam(name = "size", required = false, defaultValue = "5") int size,
			@RequestParam(name = "fieldSort", required = false, defaultValue = "null") String fieldSort,
			HttpServletRequest request) {
		List<Group> groups = searchService.search(EntityName.GROUP, fieldSearch, searchValue,
				RoleQueryCondition.conditionSearchGroupInRole(roleId));

		if (groups.size() == 0) {
			return new ResponseEntity<>(groups, HttpStatus.NOT_FOUND);
		}
		ListPaging<Group> listPagging = new ListPaging<>(groups, size, pageIndex, fieldSort, request);
		return new ResponseEntity<>(listPagging, HttpStatus.OK);
	}
}
