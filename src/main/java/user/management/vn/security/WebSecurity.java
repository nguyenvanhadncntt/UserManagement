package user.management.vn.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private FailureLoginHandler failureLoginHandle;
	
	@Autowired
	private SuccessLoginHandler successLoginHandle;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		http.authorizeRequests().antMatchers("/login**","/registerAccount**","/activeAccount**").permitAll().anyRequest().authenticated();
		
		http.authorizeRequests()
				.and().formLogin()
				.loginPage("/login").permitAll()
				.usernameParameter("email").passwordParameter("password")
				.loginProcessingUrl("/login")
				.successHandler(successLoginHandle)
				.failureHandler(failureLoginHandle)
				.and().logout().logoutUrl("/logout").permitAll()
				.deleteCookies("JSESSIONID").logoutSuccessUrl("/login?logout").permitAll().and().httpBasic();
//		http.csrf().disable().authorizeRequests().anyRequest().permitAll();
	}

	@Override
	public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web)
			throws Exception {
		web.ignoring().antMatchers("/assets/**");
	}


}
