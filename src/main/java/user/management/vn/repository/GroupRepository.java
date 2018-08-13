package user.management.vn.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import user.management.vn.entity.Group;

@Repository
@Transactional
public interface GroupRepository extends JpaRepository<Group, Long> {
	Optional<Group> findById(Long groupId);
	List<Group> findByNonDel(boolean nonDel);
	Optional<Group> findByIdAndNonDel(Long groupId,boolean nonDel);	
//	Page<Group> findAll(Pa)
}
