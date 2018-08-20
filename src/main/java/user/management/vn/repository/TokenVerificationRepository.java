package user.management.vn.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import antlr.Token;
import user.management.vn.entity.TokenVerifition;

public interface TokenVerificationRepository extends JpaRepository<TokenVerifition, Long>{
	Optional<TokenVerifition> findByTokenCode(String tokenCode);
	
	@Modifying
	@Query(value = "delete   from token_verfication where id = ?1", nativeQuery = true)
	int  deleteTokenById(Long id);
}
