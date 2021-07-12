package com.example.flight.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.flight.services.CustomerService;
import com.example.flight.services.MyUserService;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	MyUserService myUserService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth
			.userDetailsService(myUserService);
			/*
			.inMemoryAuthentication()
				.withUser("admin")
				.password(passwordEncoder().encode("admin123"))
				.authorities("ADMIN")
				.and()
				.withUser("user")
				.password(passwordEncoder().encode("user"))
				.authorities("USER");
			*/
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/login").permitAll()
			.antMatchers("/", "/home").hasAnyAuthority("ADMIN", "USER")
			.antMatchers("/flights").hasAuthority("ADMIN")
			.antMatchers("/users", "/bookflight").hasAnyAuthority("ADMIN", "USER")			
			.and()
			.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/authenticate")
				.defaultSuccessUrl("/home", true)
				.failureUrl("/login?error=true")
			.and()
			.logout()
				.logoutUrl("/cleanup1")
				.logoutSuccessUrl("/logout-success")
				//.invalidateHttpSession(false)
				//.clearAuthentication(true)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.NEVER)
			/*
			.logout((logout) ->
					logout.deleteCookies("remove")
					.invalidateHttpSession(false)
					.logoutUrl("/logout")
					.logoutSuccessUrl("/logout-success")
					);
			*/
			;
		
			
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
