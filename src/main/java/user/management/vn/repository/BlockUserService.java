package user.management.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import user.management.vn.entity.BlockUser;

public interface BlockUserService extends JpaRepository<BlockUser, Long> {

}
