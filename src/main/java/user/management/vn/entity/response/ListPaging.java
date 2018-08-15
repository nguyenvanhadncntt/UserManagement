package user.management.vn.entity.response;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ListPaging<T> implements Serializable{
	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 1L;

	private List<T> list;

	private Integer totalPage;

	public ListPaging() {
		super();
	}

	public ListPaging(List<T> list,int size
			,int pageIndex
			,String fieldSort,HttpServletRequest request) {
		PageRequest pageRequest = null;
		if(fieldSort!=null) {
			HttpSession session = request.getSession();
			String sortType = (String) session.getAttribute("sortType");
			Sort sort = null;
			if(sortType==null || sortType.equals("ASC")) {
				sort = Sort.by(fieldSort).ascending();
				session.setAttribute("sortType", "DESC");
			}else {
				sort = Sort.by(fieldSort).descending();
				session.setAttribute("sortType", "ASC");
			}
			pageRequest = new PageRequest(pageIndex, size,sort);
		}else {
			pageRequest = new PageRequest(pageIndex, size);
		}
		Page<T> listPaging = new PageImpl<>(list,pageRequest , 0);
		this.list = listPaging.getContent();
		this.totalPage = listPaging.getTotalPages();
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

}
