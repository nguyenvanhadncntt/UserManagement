package user.management.vn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import user.management.vn.entity.UserRole;
import user.management.vn.repository.UserRoleRepository;
import user.management.vn.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService{
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	/**
	 * @summary add user with role
	 * @author TaiTruong
	 * @param userRole
	 * @return UserRole
	 */
	@Override
	public UserRole addUserWithRole(UserRole userRole) {
		// TODO Auto-generated method stub
		return userRoleRepository.save(userRole);
	}

}
