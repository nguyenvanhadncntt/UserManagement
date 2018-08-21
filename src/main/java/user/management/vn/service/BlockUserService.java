package user.management.vn.service;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BlockUserService {
	BlockUserService addBlockUserService(BlockUserService blockUserService);
	BlockUserService editBlockUserService(BlockUserService blockUserService);
	
	@Modifying
	@Query(value = "delete from block_user where id = ?1", nativeQuery = true)
	int deleteBlockUser(Long id);
}
