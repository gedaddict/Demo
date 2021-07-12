package com.example.flight;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.spi.PersistenceUnitInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.entities.Flight;
import com.example.entities.Payment;
import com.example.flight.configurations.SecurityConfig;
import com.example.repositories.FlightRepository;

import net.bytebuddy.build.ToStringPlugin.Exclude;

@SpringBootApplication//(exclude = {SecurityAutoConfiguration.class, SecurityFilterAutoConfiguration.class})
@EntityScan(basePackages = "com.example.entities")
//@EnableJpaRepositories(basePackages = "com.example.repositories")
@ComponentScan(basePackages = {"com.example.flight", "com.example.repositories"})
public class FlightApplication extends SpringBootServletInitializer implements CommandLineRunner{
	
	private final static Logger log = LoggerFactory.getLogger(FlightApplication.class);
	
	@PersistenceUnit
	EntityManagerFactory emf;
	
	@Autowired
	ApplicationContext ac;
	
	@Autowired
	Flight flight;
	
	@Autowired
	Payment payment;
	
	//@Autowired
	//PasswordEncoder passwordEncoder;
	
	@Bean
	public Flight getFlightDao() {
		return new Flight();
	}
	
	@Bean
	public Payment getPaymentDao() {
		return new Payment();
	}

	public static void main(String[] args) {
		SpringApplication.run(FlightApplication.class, args);
	}
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FlightApplication.class);
	}
	
	@Override
	public void run(String... args) throws Exception {
		//loadTestData();
		String[] beanDefinitionNames = ac.getBeanDefinitionNames();
		for(String bean : beanDefinitionNames)
			System.out.println(bean);
	}
	
	private void loadTestData() {
		
		log.info("initializing data...");
	    
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		/*
		flight.setFromLocation("Manila");
		flight.setToLocation("Autralia");
		flight.setDeparture(LocalDateTime.now());
		flight.setArrival(LocalDateTime.of(LocalDate.of(2021, Month.JULY, 25), LocalTime.of(23, 45)));
		flight.setPrice(new Double(28000));
		
		payment.setCardNumber(52354262);
		payment.setBank("BPI");
		payment.setCreditBalance(150000);
		payment.setCreditLimit(150000);
		
		user.setUsername("admin");
		user.setPassword(passwordEncoder.encode("admin"));
		user.getRole().add(new UserRole("ADMIN", user));
		user.getRole().add(new UserRole("MANAGER", user));
		user.setActive(true);
		user.setLocked(false);
		*/
		List resultList = em.createQuery("from User").getResultList();
		resultList.stream().forEach(u -> em.remove(u));
		
		//em.persist(user);
		
		em.getTransaction().commit();
		em.close();
		
	}
	
}
