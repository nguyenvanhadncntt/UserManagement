package user.management.vn.service;

import java.util.List;

public interface SearchService {
	<T> List<T> search(String entityName,String columnName,String value);
	<T> List<T> search(String entityName,String columnName,String value,String condition);
}
