package user.management.vn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import user.management.vn.service.SearchService;
import user.management.vn.util.SearchGeneric;
/**
 * 
 * @summary all functional of search implement
 * @author Thehap Rok
 *
 */
@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchGeneric searchGeneric;

	@Override
	public <T> List<T> search(String entityName, String columnName, String value) {
		return searchGeneric.searchGeneric(entityName, columnName, value);
	}

	@Override
	public <T> List<T> search(String entityName, String columnName,
			String value, String condition) {
		return searchGeneric.searchGeneric(entityName, columnName, value, condition);
	}

}
