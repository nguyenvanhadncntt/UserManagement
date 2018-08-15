package user.management.vn.util;

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
		sqlQuery.append("from ");
		sqlQuery.append(tableName);
		if ((!fieldSearch.isEmpty() || fieldSearch != null) 
				&& (!searchValue.isEmpty() || searchValue != null)) {
			sqlQuery.append(" where ");
			sqlQuery.append(fieldSearch);
			sqlQuery.append(" like '%");
			sqlQuery.append(searchValue);
			sqlQuery.append("%' and nonDel=1 ");
		}
		Query query = entityManager.createQuery(sqlQuery.toString());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> searchGeneric(String tableName, String fieldSearch, String searchValue, String condition) {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("from ");
		sqlQuery.append(tableName);
		if ((!fieldSearch.isEmpty() || fieldSearch != null) 
				&& (!searchValue.isEmpty() || searchValue != null)) {
			sqlQuery.append(" where ");
			sqlQuery.append(fieldSearch);
			sqlQuery.append(" like '%");
			sqlQuery.append(searchValue);
			sqlQuery.append("%' and nonDel=1 ");
		}
		if (condition != null && !condition.isEmpty()) {
			sqlQuery.append("and ");
			sqlQuery.append(condition);
		}
		Query query = entityManager.createQuery(sqlQuery.toString());
		return query.getResultList();
	}
}
