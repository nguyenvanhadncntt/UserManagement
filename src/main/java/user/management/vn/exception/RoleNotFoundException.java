package user.management.vn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @summary Role Not Found Exception
 * @date Aug 22, 2018
 * @author bom
 * @return
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RoleNotFoundException extends RuntimeException {
	public RoleNotFoundException(String exception) {
		super(exception);
	}
}
