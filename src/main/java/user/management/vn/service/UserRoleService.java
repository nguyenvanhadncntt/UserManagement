package user.management.vn.service;

import user.management.vn.entity.UserRole;

public interface UserRoleService {
	/**
	 * @summary add user with role
	 * @author TaiTruong
	 * @param userRole
	 * @return UserRole
	 */
	UserRole addUserWithRole(UserRole userRole);
}
