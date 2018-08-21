package user.management.vn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import user.management.vn.repository.BlockUserRepository;
import user.management.vn.service.BlockUserService;

public class BlockUserServiceImpl implements BlockUserService{
	@Autowired
	private BlockUserRepository blockUserRepository;
	@Override
	public BlockUserService addBlockUserService(BlockUserService blockUserService) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BlockUserService editBlockUserService(BlockUserService blockUserService) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteBlockUser(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
