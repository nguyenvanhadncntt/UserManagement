package user.management.vn.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import user.management.vn.entity.User;

import org.springframework.data.jpa.repository.Modifying;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findById(Long userId);

  @Query("select u from User u "
			+ "where u.id not in "
			+ "(select ug.id from UserGroup ug "
			+ "where ug.group.id=:groupId) "
			+ "and (u.userDetail.fullname like %:param% or u.email like %:param% )")
	List<User> findUserNotInGroupByNameOrEmail(@Param("groupId") Long groupId, @Param("param") String nameOrEmail);

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
