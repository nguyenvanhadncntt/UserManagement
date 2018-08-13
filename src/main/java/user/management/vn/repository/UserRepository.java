package user.management.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import user.management.vn.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
}
