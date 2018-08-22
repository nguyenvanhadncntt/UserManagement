package user.management.vn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @summary Group Not Found Exception
 * @date Aug 22, 2018
 * @author bom
 * @return
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class GroupNotFoundException extends RuntimeException {
	public GroupNotFoundException(String exception) {
		super(exception);
	}
}
