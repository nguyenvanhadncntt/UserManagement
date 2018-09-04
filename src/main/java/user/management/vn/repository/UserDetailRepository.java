package user.management.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import user.management.vn.entity.UserDetail;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long>{

}
