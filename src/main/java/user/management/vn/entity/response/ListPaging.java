package user.management.vn.entity.response;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.PropertyComparator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author Thehap Rok
 *
 * @param <T>
 */
public class ListPaging<T> implements Serializable {
	@JsonIgnore
	private static final long serialVersionUID = 1L;

	private List<T> list;

	private Integer totalPage;

	private Integer currentPage;

	public ListPaging() {
		super();
	}

	/**
	 * @summary handle paging for list
	 * @author Thehap Rok
	 * @param list
	 * @param size
	 * @param pageIndex
	 * @param fieldSort
	 * @param request
	 */
	public ListPaging(List<T> list, int size, int pageIndex, String fieldSort, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String fieldSortSession = (String) session.getAttribute("fieldSort");
		if (!"null".equals(fieldSort) || fieldSortSession != null) {
			session.setAttribute("fieldSort", (fieldSortSession != null) ? fieldSortSession : fieldSort);
			String sortType = (String) session.getAttribute("sortType");
			if (sortType == null || sortType.equals("ASC")) {
				PropertyComparator.sort(list, new MutableSortDefinition(fieldSort, true, true));
				session.setAttribute("sortType", "DESC");
			} else {
				PropertyComparator.sort(list, new MutableSortDefinition(fieldSort, true, false));
				session.setAttribute("sortType", "ASC");
			}
		}

		PagedListHolder<T> pageList = new PagedListHolder<>(list);
		pageList.setPageSize(size);
		pageList.setPage(pageIndex);
		this.list = pageList.getPageList();
		this.totalPage = pageList.getPageCount();
		this.currentPage = pageList.getPage();
	}

	public void clearSession() {

	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

}
