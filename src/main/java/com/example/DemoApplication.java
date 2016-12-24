package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.filter.ForwardedHeaderFilter;

@SpringBootApplication
public class DemoApplication {
	@Bean
	public static FilterRegistrationBean forwardedHeaderFilter() {
		FilterRegistrationBean filter = new FilterRegistrationBean(new ForwardedHeaderFilter());
		filter.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return filter;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
