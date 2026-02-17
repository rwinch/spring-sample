

package com.example.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

import reactor.core.publisher.Hooks;


@SpringBootApplication
public class DemoApplication {

    static void main(final String[] args) {
        Hooks.enableAutomaticContextPropagation();
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public SecurityWebFilterChain securityFilterChain(final ServerHttpSecurity http) {
        return http.securityMatcher(ServerWebExchangeMatchers.pathMatchers(HttpMethod.GET, "/foo")).build();
    }
}
