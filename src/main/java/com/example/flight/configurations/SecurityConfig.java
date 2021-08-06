package com.example.flight.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.flight.services.MyUserService;

@Configuration
@EnableWebSecurity
@Profile("dev")
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private final MyUserService myUserService;
	private final PasswordEncoder passwordEncoder;
	
	public SecurityConfig(MyUserService myUserService, PasswordEncoder passwordEncoder) {
		this.myUserService = myUserService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth
			.userDetailsService(myUserService);
			/*
			.inMemoryAuthentication()
				.withUser("admin")
				.password(passwordEncoder.encode("admin"))
				.authorities("ADMIN")
				.and()
				.withUser("user")
				.password(passwordEncoder.encode("user"))
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
			.antMatchers("/flights").hasAnyAuthority("ADMIN", "USER")
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
			.sessionCreationPolicy(SessionCreationPolicy.NEVER);
			/*
			.logout((logout) ->
					logout.deleteCookies("remove")
					.invalidateHttpSession(false)
					.logoutUrl("/logout")
					.logoutSuccessUrl("/logout-success")
					);
			
			;	
			*/
	}
	
}
