package user.management.vn.config;

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
		
		http.authorizeRequests().anyRequest().authenticated();
		
		http.authorizeRequests()
				.and().formLogin()
				.loginPage("/login").usernameParameter("email").passwordParameter("password")
				.loginProcessingUrl("/login").permitAll().defaultSuccessUrl("/	")
				.failureUrl("/login?login_error=false").and().logout().logoutUrl("/logout")
				.deleteCookies("JSESSIONID").logoutSuccessUrl("/login?logout").permitAll().and().httpBasic();
//		http.csrf().disable().authorizeRequests().anyRequest().permitAll();
	}

	@Override
	public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web)
			throws Exception {
		web.ignoring().antMatchers("css/**", "js/**");
	}
}
