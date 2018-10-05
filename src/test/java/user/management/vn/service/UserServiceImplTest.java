package user.management.vn.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import user.management.vn.UserManagementApplication;
import user.management.vn.entity.Role;
import user.management.vn.entity.User;
import user.management.vn.entity.UserRole;
import user.management.vn.entity.dto.UserDTO;
import user.management.vn.exception.UserAlreadyRoleException;
import user.management.vn.exception.UserNotFoundException;
import user.management.vn.repository.RoleRepository;
import user.management.vn.repository.UserGroupRepository;
import user.management.vn.repository.UserRepository;
import user.management.vn.repository.UserRoleRepository;
import user.management.vn.test.config.MailTestConfig;
import user.management.vn.util.RoleSystem;

@Profile("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes=UserManagementApplication.class)
@Import(MailTestConfig.class)
public class UserServiceImplTest {

	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository; 
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserGroupRepository userGroupRepository2;
	
	@Autowired
	UserRoleRepository userRoleRepository; 
	
	/**
	 * @summary test upgrade User To Admin
	 * @author Thehap Rok
	 * @return void
	 */
	@Test
	public void upgradeUserToAdminTest() {
		Long userId = 1l;
		UserRole userRole = userService.upgradeUserRole(userId,1l);
		Role role = roleRepository.findByRoleName(RoleSystem.ADMIN);
		assertEquals(userRole.getUser().getId(), userId);
		assertEquals(userRole.getRole().getId(), role.getId());
	}
	
	/**
	 * @summary test method upgradeUserToAdmin have exception UserNotFoundException
	 * @author Thehap Rok
	 * @return void
	 */
	@Test(expected=UserNotFoundException.class)
	public void upgradeUserToAdminUserNotFoundException() {
		Long userId = 1000l;
		userService.upgradeUserRole(userId,1l);
	}
	
	/**
	 * @summary test method upgradeUserToAdmin have exception UserAlrealdyAdminException
	 * @author Thehap Rok
	 * @return void
	 */
	@Test(expected=UserAlreadyRoleException.class)
	public void upgradeUserToAdminUserAlrealdyAdminException() {
		Long userId = 2l;
		userService.upgradeUserRole(userId,1l);
	}
	
	@Test
	public void addUserTest() {
		UserDTO userDTO = new UserDTO();
		userDTO.setAddress("Da Nang");
		Date now = new Date();
		userDTO.setBirthday(now);
		userDTO.setEmail("thai@gmail.com");
		userDTO.setFullname("Thai Dui");
		userDTO.setGender(true);
		userDTO.setPassword("123");
		userDTO.setMatchingPassword("123");
		userDTO.setPhone("123456789");
		User user = userService.addUser(userDTO);
		
		assertEquals(userDTO.getAddress(), user.getUserDetail().getAddress());
		assertEquals(userDTO.getFullname(), user.getUserDetail().getFullname());
		assertEquals(now, user.getUserDetail().getBirthDay());
	}
	
}
