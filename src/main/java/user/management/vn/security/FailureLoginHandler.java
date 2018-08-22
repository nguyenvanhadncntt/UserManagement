package user.management.vn.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import user.management.vn.util.LoginErrorMessage;
/**
 * @summary handle authentication fail
 * @author Thehap Rok
 */

@Component
public class FailureLoginHandler extends SimpleUrlAuthenticationFailureHandler {
	
	/**
	 * get exception of login fail
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String messageError = LoginErrorMessage.loginErrorMessage(exception);
		request.getSession().setAttribute("error", messageError);
		getRedirectStrategy().sendRedirect(request, response, "/login");
//		response.sendRedirect("/login?error");
	}

}
