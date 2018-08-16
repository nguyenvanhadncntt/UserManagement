package user.management.vn.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import user.management.vn.entity.UserGroup;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

	@Query("select ug from UserGroup ug where ug.group.id = ?1 and ug.group.nonDel = 1")
	List<UserGroup> findAllUserOfGroupId(Long groupId);

	@Query("select ug from UserGroup ug where ug.group.id = ?1 and ug.user.id = ?2 "
			+ "and ug.group.nonDel = 1 and ug.user.nonDel = 1")
	Optional<UserGroup> findUserById(Long groupId, Long userId);

	@Modifying
	@Query("delete from UserGroup ug where ug.group.id=?1 and ug.user.id=?2")
	Integer deleteUserFromGroup(Long groupId, Long userId);
	
	boolean existsByGroupIdAndUserId(Long groupId,Long userId);	
}
