package user.management.vn.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import user.management.vn.entity.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
	Optional<Group> findById(Long groupId);
}