package com.fireway.cpms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
@SpringBootApplication
@ServletComponentScan(basePackages = {
		"com.fireway.cpms.service",
		"com.fireway.cpms.filter",
		"com.fireway.cpms.listener"
})
@EnableAutoConfiguration(exclude = {
		SecurityAutoConfiguration.class})
public class CpmsApplication {

	@Autowired
	private Environment env;

//	@RequestMapping("/")
//	String home() {
//		return "Hello World!";
//	}

//	@Bean
//	public FilterRegistrationBean securityFilterChainRegistration() {
//		DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();
//		delegatingFilterProxy.setTargetBeanName(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME);
//		FilterRegistrationBean registrationBean = new FilterRegistrationBean(delegatingFilterProxy);
//		registrationBean.setName(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME);
//		registrationBean.addUrlPatterns("/*");
//		return registrationBean;
//	}
//	@Bean public ServletRegistrationBean jerseyServlet() {
//		ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/rest/*");
//		// our rest resources will be available in the path /rest/*
//		registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());
//		return registration;
//	}
//	@Bean
//	public ServletRegistrationBean dispatcherServlet(){
//		ServletRegistrationBean registration = new ServletRegistrationBean(new DispatcherServlet(), "/spring/*");
//
//		return registration;
//	}

	public static void main(String[] args) {
		SpringApplication.run(CpmsApplication.class, args);
	}

//	@Bean(name = "dataSource")
//	public DataSource getDataSource() {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//		// See: application.properties
//		dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
//		dataSource.setUrl(env.getProperty("spring.datasource.url"));
//		dataSource.setUsername(env.getProperty("spring.datasource.username"));
//		dataSource.setPassword(env.getProperty("spring.datasource.password"));
//
//		System.out.println("## getDataSource: " + dataSource);
//
//		return dataSource;
//	}

//	@Bean
//	public ServletRegistrationBean assigneeServlet() {
//		return new ServletRegistrationBean(new AssigneeServlet(), "/assignee");
//	}
//
//	@Bean
//	public ServletRegistrationBean deploymentServlet() {
//		return new ServletRegistrationBean(new DeploymentServlet(), "/deployment");
//	}
//
//	@Bean
//	public ServletRegistrationBean hashServlet() {
//		return new ServletRegistrationBean(new HashServlet(), "/hash");
//	}
//	@Bean
//	public ServletRegistrationBean helloServlet() {
//		return new ServletRegistrationBean(new HelloServlet(), "/hello");
//	}
//
//	@Bean
//	public ServletRegistrationBean loginServlet() {
//		return new ServletRegistrationBean(new LoginServlet(), "/login");
//	}
//
//	@Bean
//	public ServletRegistrationBean logoutServlet() {
//		return new ServletRegistrationBean(new LogoutServlet(), "/logout");
//	}
//
//	@Bean
//	public ServletRegistrationBean messageServlet() {
//		return new ServletRegistrationBean(new MessageServlet(), "/message");
//	}
//
//
//	@Bean
//	public ServletRegistrationBean logServlet() {
//		return new ServletRegistrationBean(new LogServlet(), "/log");
//	}
//
//	@Bean
//	public ServletRegistrationBean memberServlet() {
//		return new ServletRegistrationBean(new MemberServlet(), "/member");
//	}
//
//	@Bean
//	public ServletRegistrationBean positionServlet() {
//		return new ServletRegistrationBean(new PositionServlet(), "/position");
//	}
//
//	@Bean
//	public ServletRegistrationBean projectServlet() {
//		return new ServletRegistrationBean(new ProjectServlet(), "/project");
//	}
//
//	@Bean
//	public ServletRegistrationBean resourceServlet() {
//		return new ServletRegistrationBean(new ResourceServlet(), "/resources/*");
//	}
//
//	@Bean
//	public ServletRegistrationBean roleServlet() {
//		return new ServletRegistrationBean(new RoleServlet(), "/role");
//	}
//
//	@Bean
//	public ServletRegistrationBean stageServlet() {
//		return new ServletRegistrationBean(new StageServlet(), "/stage");
//	}
//
//	@Bean
//	public ServletRegistrationBean startupServlet() {
//		return new ServletRegistrationBean(new StartupServlet(), "/startup");
//	}
//
//	@Bean
//	public ServletRegistrationBean templateServlet() {
//		return new ServletRegistrationBean(new TemplateServlet(), "/template");
//	}
//
//	@Bean
//	public ServletRegistrationBean typeServlet() {
//		return new ServletRegistrationBean(new TypeServlet(), "/type");
//	}
//
//	@Bean
//	public ServletRegistrationBean userServlet() {
//		return new ServletRegistrationBean(new UserServlet(), "/user");
//	}
}
