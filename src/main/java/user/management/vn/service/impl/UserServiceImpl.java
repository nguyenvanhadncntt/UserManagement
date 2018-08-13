package user.management.vn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import user.management.vn.entity.User;
import user.management.vn.repository.UserRepository;
import user.management.vn.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getAllUserOfGroup(Long groupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findAllUser() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

}
