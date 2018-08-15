package user.management.vn;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

@Component
public class SearchGeneric {

	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public <T> List<T> searchGeneric(String tableName, String fieldSearch, String searchValue) {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("select tb from ");
		sqlQuery.append(tableName);
		if("UserDetail".equals(tableName)) {
			sqlQuery.append(" tb where tb.user.nonDel=1 and tb.");
		}
		sqlQuery.append(fieldSearch);
		sqlQuery.append(" like '%");
		sqlQuery.append(searchValue);
		sqlQuery.append("%' and tb.nonDel=1 ");
		Query query = entityManager.createQuery(sqlQuery.toString());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> searchGeneric(String tableName, String fieldSearch, String searchValue,String condition) {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("select tb from ");
		sqlQuery.append(tableName);
		if("UserDetail".equals(tableName)) {
			sqlQuery.append(" tb where tb.user.nonDel=1 and tb.");
		}
		sqlQuery.append(fieldSearch);
		sqlQuery.append(" like '%");
		sqlQuery.append(searchValue);
		sqlQuery.append("%' and tb.nonDel=1 ");
		if(condition!= null && !condition.isEmpty()) {
			sqlQuery.append(condition);
		}
		Query query = entityManager.createQuery(sqlQuery.toString());
		return query.getResultList();
	}
}
