package com.example.flight.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.entities.Customer;
import com.example.entities.MyUser;
import com.example.flight.services.CustomerService;
import com.example.flight.services.MyUserService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	MyUserService myUserService;
	
	@Autowired
	MyUser myUser;
	
	@Autowired
	PasswordEncoder pE;
	
	private ModelAndView mav = new ModelAndView();
	private UsernamePasswordAuthenticationToken auth;
	
	@Bean
	public MyUser getMyUser() {
		return new MyUser();
	}
	
	@Value("${name:name not found in application.properties}")
	private String name;
	
	@GetMapping("")
	public ModelAndView index(HttpServletRequest request, Authentication auth) {
		log.info("processing root...");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();
		auth = SecurityContextHolder.getContext().getAuthentication();
		//UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, credentials);
		//SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println("auth >>>>>"+auth);
		System.out.println("getPrincipal >>>>>"+principal);
		System.out.println("getCredentials >>>>>"+credentials);
		System.out.println("getAuthentication >>>>>"+SecurityContextHolder.getContext().getAuthentication());
		System.out.println("getSession >>>>>"+request.getSession(false));
		System.out.println("getSessionId >>>>>"+request.getSession().getId());
		System.out.println("getSession isNew >>>>>"+request.getSession().isNew());
		System.out.println("isRequestedSessionIdValid >>>>>"+request.isRequestedSessionIdValid());
		System.out.println("getCookies >>>>>"+request.getCookies().toString());
		System.out.println("getCookies length >>>>>"+request.getCookies().length);
		
		//mav = new ModelAndView();
		if (!auth.isAuthenticated())
			//mav.addObject("username", SecurityContextHolder.getContext().getAuthentication().getName());
			return new ModelAndView("login");
		
		else
			return new ModelAndView("redirect:/home");
		//return mav;
		//return new ModelAndView("redirect:/flights");
	}
	
	@GetMapping("/login")
	public ModelAndView login(HttpServletRequest request, Authentication auth) {
		log.info("rendering login page");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();
		if (auth == null) {
			auth = new UsernamePasswordAuthenticationToken(principal, credentials);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		System.out.println("auth >>>>>"+auth);
		System.out.println("getAuthentication >>>>>"+SecurityContextHolder.getContext().getAuthentication());
		System.out.println("getSession >>>>>"+request.getSession(false));
		System.out.println("getSessionId >>>>>"+request.getSession().getId());
		System.out.println("getSession isNew >>>>>"+request.getSession().isNew());
		System.out.println("isRequestedSessionIdValid >>>>>"+request.isRequestedSessionIdValid());
		mav.getModelMap().clear();
		if (!auth.isAuthenticated())
			mav.setViewName("login");
			//return new ModelAndView("login");
		else
			mav.setViewName("redirect:/home");
			//return new ModelAndView("redirect:/home");
	return mav;
	}
	
	@GetMapping("/home")
	public ModelAndView home(Authentication authentication) {
		log.info("rendering Home page");
		String username = authentication.getName();
		//ModelAndView mav = new ModelAndView();
		mav.setViewName("home");
		mav.addObject("username", username);
		return mav;
	}
	
	@GetMapping("/logout-success")
	public ModelAndView logoutSuccess() {
		log.info("logout-success... redirecting to login page...");
		mav.getModel().clear();
		mav.setViewName("redirect:/login?logout=true");
		//return new ModelAndView("redirect:/login?logout=true");
		return mav;
	}
	
	@GetMapping("/cleanup")
	public ModelAndView cleanup() {
		log.info("processing LOGOUT request...");
		log.info("clearing credentials...");
		SecurityContextHolder.getContext().setAuthentication(null);
		mav.setViewName("redirect:/login?logout=true");
		//return new ModelAndView("redirect:/login?logout=true");
		return mav;
	}
	
	@PostMapping("/register")
	public MyUser register(@RequestBody MyUser user) {
		log.info("inside register");
		return myUserService.addUser(user);
	}
	
	@GetMapping("/users")
	@ResponseBody
	public List<MyUser> getAllUsers(HttpServletRequest httpServletRequest) {
		System.out.println("Admin >>>>>"+httpServletRequest.isUserInRole("ADMIN"));
		System.out.println("getName >>>>>"+SecurityContextHolder.getContext().getAuthentication().getName());
		System.out.println("getAuthorities >>>>>"+SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		return myUserService.getAllUsers();
	}
	
	@GetMapping("/users/{username}")
	public MyUser getUser(@PathVariable("username") String username) {
		return myUserService.getUser(username);
	}
	
	@PutMapping("/users/{username}")
	public MyUser updateUser(@PathVariable("username") String username, @RequestBody MyUser user) {
		return myUserService.updateUser(username, user);
	}
	
	@DeleteMapping("/users/{username}")
	public String deleteUser(@PathVariable("username") String username) {
		log.info("executing ddelete");
		myUserService.deleteUser(username);
		return username + " deleted";
	}
	
	@GetMapping("/error")
	@ResponseBody
	public String error() {
		return "Service unavailable...something came up.";
	}

}
