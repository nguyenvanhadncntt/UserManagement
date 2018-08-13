package user.management.vn.entity.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ListIdWrapper implements Serializable{
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	
	private List<Long> ids;
	public ListIdWrapper() {
		super();
	}
	public List<Long> getIds() {
		return ids;
	}
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	
	
}
