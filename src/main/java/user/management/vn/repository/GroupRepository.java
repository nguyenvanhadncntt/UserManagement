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

	/**
	 * @summary search group base on nonDel column
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param nonDel
	 * @return
	 * @return List<Group>
	 */
	List<Group> findByNonDel(Boolean nonDel);

	/**
	 * 
	 * @summary search group object base on id and nonDel
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param groupId
	 * @param nonDel
	 * @return
	 * @return Optional<Group>
	 */
	Optional<Group> findByIdAndNonDel(Long groupId, Boolean nonDel);

	/**
	 * 
	 * @summary search group in GroupRoles object base on role id
	 * @date Aug 16, 2018
	 * @author Tai
	 * @param roleId
	 * @return
	 * @return List<Group>
	 */
	List<Group> findByGroupRoles_Role(Long roleId);

}
