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

import user.management.vn.entity.Role;
import user.management.vn.entity.response.ListPaging;
import user.management.vn.service.RoleService;

@RestController
@RequestMapping("/api/roles")
public class RoleApiController {
	
	@Autowired
	private RoleService roleService;
	
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
	public ResponseEntity<Object> getAllUser(
			@RequestParam(name = "index", required = false, defaultValue = "0") int pageIndex,			 								
			@RequestParam(name = "size", required= false, defaultValue = "5") int size,			 								
			@RequestParam(name= "fieldSort", required = false, defaultValue = "null") String fieldSort,
			HttpServletRequest request) {
		List<Role> listRole = roleService.getAllRoles();
		if(listRole.size() == 0) {
			return new ResponseEntity<>(listRole, HttpStatus.NOT_FOUND);
		}
		ListPaging<Role> listPaging = new ListPaging<>(listRole, size, pageIndex, fieldSort, request);
		return new ResponseEntity<>(listRole, HttpStatus.OK);		
	}

	/**
	* @summary api get 1 role from database base on id of role
	* @date Aug 15, 2018
	* @author ThaiLe
	* @param id
	* @return ResponseEntity<Object>
	 */
	@GetMapping(path= "/{id}")
	public ResponseEntity<Object>  getRoleById(@PathVariable("id") long id) {
		Role objRole = roleService.getRoleById(id);
		if(objRole == null) {
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
		if(objRole == null) {
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
		if(oldRole != null) {
			return new ResponseEntity<String>("Role was existed", HttpStatus.CONFLICT);
		}
		Role newRole = null;
		try {			
			newRole = roleService.addRole(role);			
		} catch (Exception e) {
			
			System.out.println("Error: "+ e.getMessage());
			return new ResponseEntity<String>("Can't add role", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("Add role successfully", HttpStatus.OK);		
	}
	
	/**
	* @summary api edit a role from  database 
	* @date Aug 15, 2018
	* @author ThaiLe
	* @param role
	* @return ResponseEntity<String>
	 */
	@PutMapping
	public ResponseEntity<String> editRole(@Valid @RequestBody Role role) {
		Role oldRole = roleService.getRoleById(role.getId());
		if(oldRole == null ) {
			return new ResponseEntity<String>("Not found role", HttpStatus.NOT_FOUND);
		}
		Role editRole = null;
		try {
			editRole = roleService.editRole(role);			
		} catch (Exception e) {			
			System.out.println(e.getMessage());
			return new ResponseEntity<String>("Can't edit role", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("Edit role successfully"+ editRole.getRoleName(), HttpStatus.NOT_FOUND);
	}
}