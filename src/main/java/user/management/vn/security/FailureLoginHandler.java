package user.management.vn.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
/**
 * @summary handle authentication fail
 * @author Thehap Rok
 */

public class FailureLoginHandler extends SimpleUrlAuthenticationFailureHandler {
	/**
	 * get exception of login fail
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		request.getSession().setAttribute("error", exception.getMessage());
		request.setAttribute("test", "test");
		getRedirectStrategy().sendRedirect(request, response, "/login");
//		response.sendRedirect("/login?error");
	}

}
