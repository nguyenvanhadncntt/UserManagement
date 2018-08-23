package user.management.vn.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import user.management.vn.entity.BlockUser;
import user.management.vn.entity.User;
import user.management.vn.service.BlockUserService;
import user.management.vn.service.UserService;

@Component
public class SuccessLoginHandler implements AuthenticationSuccessHandler{
	
	@Autowired 
	private UserService userSevice;
	
	@Autowired
	private BlockUserService blockUserService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Object principal = authentication.getPrincipal();
		String email = null;
		if (principal instanceof UserDetails) {
		     email = ((UserDetails) principal).getUsername();
		} else {
		     email = principal.toString();
		}
		System.out.println(email);
		
		User objUser = userSevice.getUserByEmail(email);
		System.out.println(objUser.getEmail());
		BlockUser blockUser = objUser.getBlockUser();
		if(blockUser != null) {
			blockUserService.deleteBlockUser(blockUser.getId());			
			System.out.println("blloci user login successs");
		}		
		response.sendRedirect("/home");
	}

}
