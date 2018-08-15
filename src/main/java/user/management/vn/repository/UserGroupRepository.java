package user.management.vn.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import user.management.vn.entity.UserGroup;

@Repository
@Transactional
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

	@Query("select ug from UserGroup ug where ug.group.id = ?1 and ug.group.nonDel = 1")
	List<UserGroup> findAllUserOfGroupId(Long groupId);
	
	@Modifying
	@Query("delete from UserGroup ug where ug.group.id=?1 and ug.user.id=?2")
	Integer deleteUserFromGroup(Long groupId, Long userId);
	
}
