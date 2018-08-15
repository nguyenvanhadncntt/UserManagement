package user.management.vn.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import user.management.vn.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findById(Long userId);

	/**
	 * 
	* @summary
	* @date Aug 13, 2018
	* @author Thai
	* @param id
	* @return
	* @return int
	 */
	@Modifying
	@Query(value = "update user set non_del = 0 where id=?1", nativeQuery = true)
	int deleteUser(Long id);
	
	/**
	 * 
	* @summary
	* @date Aug 13, 2018
	* @author Thai
	* @param email
	* @return
	* @return Optional<User>
	 */
	Optional<User> findByEmail(String email);
	
	
	
}
