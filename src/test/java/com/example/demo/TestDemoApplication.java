package com.example.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.experimental.boot.server.exec.CommonsExecWebServerFactoryBean;
import org.springframework.experimental.boot.test.context.OAuth2ClientProviderIssuerUri;

import static org.springframework.experimental.boot.server.exec.MavenClasspathEntry.springBootStarter;

@TestConfiguration(proxyBeanMethods = false)
class TestDemoApplication {

    @Bean
    @OAuth2ClientProviderIssuerUri(providerName = "sample")
    static CommonsExecWebServerFactoryBean authorizationServer() {
        // @formatter:off
        return CommonsExecWebServerFactoryBean.builder()
                .useGenericSpringBootMain()
                .classpath((classpath) -> classpath
                        .entries(springBootStarter("oauth2-authorization-server"))
                );
        // @formatter:on
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.from(DemoApplication::main)
                .with(TestDemoApplication.class)
                .run(args);
    }

}
