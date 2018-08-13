package user.management.vn.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import user.management.vn.entity.User;

@Transactional
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findById(Long userId);
}
