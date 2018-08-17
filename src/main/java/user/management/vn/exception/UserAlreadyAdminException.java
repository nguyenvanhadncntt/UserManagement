package user.management.vn.exception;

public class UserAlreadyAdminException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserAlreadyAdminException(String message) {
		super(message);
	}
}
