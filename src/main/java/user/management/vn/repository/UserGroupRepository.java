package user.management.vn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import user.management.vn.entity.Group;
import user.management.vn.entity.UserGroup;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
	@Query(value = "select ug.* from user_group ug join user u where group_id=?1 and non_del=1"
			, nativeQuery = true)
	List<UserGroup> findAllUserOfGroupId(Long groupId);
}
