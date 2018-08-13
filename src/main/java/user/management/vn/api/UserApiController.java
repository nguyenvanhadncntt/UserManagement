package user.management.vn.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import user.management.vn.entity.User;
import user.management.vn.service.UserService;



@RestController
@RequestMapping("/api")
public class UserApiController {
	public static Logger logger = LoggerFactory.getLogger(UserApiController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> listAll = userService.findAllUser();
		if(listAll.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(listAll, HttpStatus.OK);
	}	
}
