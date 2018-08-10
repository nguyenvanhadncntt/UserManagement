package user.management.vn.service;

import java.util.List;

import user.management.vn.entity.User;

public interface UserService {
	List<User> getAllUserOfGroup(Long groupId);
}
