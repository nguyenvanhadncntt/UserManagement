package user.management.vn.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import user.management.vn.entity.BlockUser;

@Repository
@Transactional
public interface BlockUserRepository extends JpaRepository<BlockUser, Long> {

}
