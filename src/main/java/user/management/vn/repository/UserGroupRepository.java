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
	@Query(value = "select ug.* from user_group ug join user u on u.id=ug.user_id where group_id=?1 and non_del=1"
			, nativeQuery = true)
	List<UserGroup> findAllUserOfGroupId(Long groupId);
	
	@Modifying
	@Query(value = "delete from user_group where group_id=?1 and user_id=?2", nativeQuery = true)
	Integer deleteUserFromGroup(Long groupId, Long userId);
}
