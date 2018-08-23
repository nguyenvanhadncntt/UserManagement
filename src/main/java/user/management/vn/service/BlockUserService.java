package user.management.vn.service;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import user.management.vn.entity.BlockUser;

public interface BlockUserService {
	/**
	 * 
	* @summary add BlockUser object
	* @date Aug 22, 2018
	* @author ThaiLe
	* @param blockUser
	* @return BlockUser
	 */
	BlockUser addBlockUser(BlockUser blockUser);
	
	/**
	 * 
	* @summary edit BlockUser object
	* @date Aug 22, 2018
	* @author ThaiLe
	* @param blockUser
	* @return BlockUser
	 */
	BlockUser editBlockUser(BlockUser blockUser);
	
	
	boolean deleteBlockUser(Long id);
}
