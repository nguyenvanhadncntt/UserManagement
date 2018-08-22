package user.management.vn.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import user.management.vn.entity.User;
import user.management.vn.entity.UserRole;
import user.management.vn.repository.UserRoleRepository;
import user.management.vn.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User user = userService.getUserByEmail(username);
			List<GrantedAuthority> listGrantedAuthority = getAllRoleOfUser(user.getId());
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
					user.getEnable(), user.getNonDel(), true, user.getNonLocked(), listGrantedAuthority);
		} catch ( NullPointerException e ) {
			throw new UsernameNotFoundException("User Not Found!!!");
		}
	}

	public List<GrantedAuthority> getAllRoleOfUser(Long userId) {
		List<GrantedAuthority> listGrantedAuthority = new ArrayList<>();
		List<UserRole> listUserRole = userRoleRepository.findByUserId(userId);
		for (UserRole userRole : listUserRole) {
			String roleName = userRole.getRole().getRoleName();
			listGrantedAuthority.add(new SimpleGrantedAuthority(roleName));
		}
		return listGrantedAuthority;
	}
}
