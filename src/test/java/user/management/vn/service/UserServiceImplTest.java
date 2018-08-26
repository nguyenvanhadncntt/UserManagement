package user.management.vn.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import user.management.vn.UserManagementApplication;
import user.management.vn.entity.Group;
import user.management.vn.entity.Role;
import user.management.vn.entity.User;
import user.management.vn.entity.UserDetail;
import user.management.vn.entity.UserGroup;
import user.management.vn.entity.UserRole;
import user.management.vn.entity.response.UserResponse;
import user.management.vn.exception.UserAlreadyAdminException;
import user.management.vn.exception.UserNotFoundException;
import user.management.vn.repository.RoleRepository;
import user.management.vn.repository.UserGroupRepository;
import user.management.vn.repository.UserRepository;
import user.management.vn.repository.UserRoleRepository;
import user.management.vn.service.impl.UserServiceImpl;
import user.management.vn.test.config.MailTestConfig;
import user.management.vn.util.RoleSystem;

@Profile("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes=UserManagementApplication.class)
@Import(MailTestConfig.class)
public class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userServiceImplMock;

	@Mock
	UserGroupRepository userGroupRepository;
	
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
	
	List<User> listUser;
	
	List<UserGroup> userGroups;

	List<UserResponse> listUserResponse;

	List<UserRole> userRoles;
	
	/**
	 * @summary fake data to test
	 * @date Aug 23, 2018
	 * @author Thehap Rok
	 * @return void
	 */
	@Before
	public void setUp() {
		Role adminRole = new Role("ADMIN", "SYSTEM");
		Role userRole = new Role("USER", "SYSTEM");

		List<Role> listRole = new ArrayList<Role>();
		listRole.add(userRole);

		UserDetail ud1 = new UserDetail("ha", "01223546613", "125 Ngo Gia Thanh", false, new Date("22/02/1996"));
		UserDetail ud2 = new UserDetail("tai", "0905589750", "125 Ngo Gia Thanh", false, new Date("22/02/1996"));
		UserDetail ud3 = new UserDetail("thai", "01225784323", "125 Ngo Gia Thanh", false, new Date("22/02/1996"));

		User ha = new User("Ha@gmail.com", "123");
		User tai = new User("Tai@gmail.com", "123");
		User thai = new User("thai@gmail.com", "123");
		ha.setUserDetail(ud1);
		ha.setId(1l);
		tai.setUserDetail(ud2);
		tai.setId(2l);
		thai.setUserDetail(ud3);
		thai.setId(3l);

		userRoles = new ArrayList<>();

		UserRole urr1 = new UserRole(ha, userRole);
		UserRole urr2 = new UserRole(tai,userRole);
		UserRole urr3 = new UserRole(thai,userRole);
		
		userRoles.add(urr1);
		userRoles.add(urr2);
		userRoles.add(urr3);
		
		ha.setUserRoles(userRoles);
		tai.setUserRoles(userRoles);
		thai.setUserRoles(userRoles);
		
		Group gr1 = new Group("g1", "group 1");
		Group gr2 = new Group("g2", "group 2");
		Group gr3 = new Group("g3", "group 3");

		UserResponse ur1 = new UserResponse();
		ur1.addPropertiesFromUser(ha);
		UserResponse ur2 = new UserResponse();
		ur2.addPropertiesFromUser(tai);
		UserResponse ur3 = new UserResponse();
		ur3.addPropertiesFromUser(thai);

		UserGroup ug1 = new UserGroup(1l, ha, gr1, new Date("2018/09/09"));
		UserGroup ug2 = new UserGroup(2l, tai, gr2, new Date("2018/09/09"));
		UserGroup ug3 = new UserGroup(3l, thai, gr3, new Date("2018/09/09"));
		
		userGroups = new ArrayList<UserGroup>();

		userGroups.add(ug1);
		userGroups.add(ug2);
		userGroups.add(ug3);


		listUser = new ArrayList<>();
		listUser.add(ha);
		listUser.add(tai);
		listUser.add(thai);
		
		listUserResponse = new ArrayList<UserResponse>();
		listUserResponse.add(ur1);
		listUserResponse.add(ur2);
		listUserResponse.add(ur3);

	}

	/**
	 * @summary test method get all user of group 
	 * @date Aug 23, 2018
	 * @author Thehap Rok
	 * @return void
	 */
	@Test
	public void getAllUserOfGroup() {

		when(userGroupRepository.findAllUserOfGroupId(anyLong())).thenReturn(userGroups);
		List<UserResponse> listUr = userServiceImplMock.getAllUserOfGroup(1l);
		verify(userGroupRepository,times(1)).findAllUserOfGroupId(anyLong());
		for (int i = 0; i < listUr.size(); i++) {
			UserResponse urTest = listUr.get(i);
			UserResponse urRoot = listUserResponse.get(i);
			assertEquals(urTest.getId(), urRoot.getId());
			assertEquals(urTest.getFullname(), urRoot.getFullname());
			assertEquals(urTest.getEmail(), urRoot.getEmail());
		}
	}
	
	/**
	 * @summary test method convert list usergroup to list user 
	 * @date Aug 23, 2018
	 * @author Thehap Rok
	 * @return void
	 */
	@Test
	public void convertUserGroupsToUsers() {
		setUp();
		List<User> listUr=userServiceImplMock.convertUserGroupsToUsers(userGroups);
		for (int i = 0; i < listUr.size(); i++) {
			User urTest = listUr.get(i);
			User urRoot = listUser.get(i);
			assertEquals(urTest.getId(), urRoot.getId());
			assertEquals(urTest.getEmail(), urRoot.getEmail());
		}
	}

	/**
	 * @summary test method convert userRoles To Users
	 * @date Aug 23, 2018
	 * @author Thehap Rok
	 * @return void
	 */
	@Test
	public void convertUserRolesToUsers() {
		List<User> listUr = userServiceImplMock.convertUserRolesToUsers(userRoles);
		for (int i = 0; i < listUr.size(); i++) {
			User urTest = listUr.get(i);
			User urRoot = listUser.get(i);
			assertEquals(urTest.getId(), urRoot.getId());
		}
	}
	
	/**
	 * @summary test method convert list user to list userresponse 
	 * @date Aug 23, 2018
	 * @author Thehap Rok
	 * @return void
	 */
	@Test
	public void convertUserToUserResponse() {
		List<UserResponse> listU = userServiceImplMock.convertUserToUserResponse(listUser);
		for (int i = 0; i < listU.size(); i++) {
			UserResponse urTest = listU.get(i);
			UserResponse urRoot = listUserResponse.get(i);
			assertEquals(urTest.getId(), urRoot.getId());
			assertEquals(urTest.getFullname(), urRoot.getFullname());
			assertEquals(urTest.getEmail(), urRoot.getEmail());
		}
	}
	
	/**
	 * @summary test upgrade User To Admin
	 * @author Thehap Rok
	 * @return void
	 */
	@Test
	public void upgradeUserToAdminTest() {
		Long userId = 1l;
		UserRole userRole = userService.upgradeUserToAdmin(userId);
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
		userService.upgradeUserToAdmin(userId);
	}
	
	/**
	 * @summary test method upgradeUserToAdmin have exception UserAlrealdyAdminException
	 * @author Thehap Rok
	 * @return void
	 */
	@Test(expected=UserAlreadyAdminException.class)
	public void upgradeUserToAdminUserAlrealdyAdminException() {
		Long userId = 2l;
		userService.upgradeUserToAdmin(userId);
	}
	
	
	
}
