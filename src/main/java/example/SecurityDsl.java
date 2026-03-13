package example;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.Filter;

import org.springframework.beans.factory.BeanRegistrar;
import org.springframework.beans.factory.BeanRegistry;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.security.web.header.writers.CacheControlHeadersWriter;
import org.springframework.security.web.header.writers.HstsHeaderWriter;
import org.springframework.security.web.header.writers.XContentTypeOptionsHeaderWriter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

public class SecurityDsl implements BeanRegistrar {

    public void register(BeanRegistry registry, Environment env) {
        List<Filter> securityFilters = new ArrayList<>();
        securityFilters.add(new HeaderWriterFilter(List.of(
            new XContentTypeOptionsHeaderWriter(),
            new XXssProtectionHeaderWriter(),
            new CacheControlHeadersWriter(),
            new HstsHeaderWriter(),
            new XFrameOptionsHeaderWriter()
        )));

        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager(
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build()
        );
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        ProviderManager authenticationManager = new ProviderManager(authenticationProvider);

        UsernamePasswordAuthenticationFilter formLoginFilter = new UsernamePasswordAuthenticationFilter(authenticationManager);
        formLoginFilter.setSecurityContextRepository(new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        ));

        DefaultLoginPageGeneratingFilter loginPageFilter = new DefaultLoginPageGeneratingFilter();
        loginPageFilter.setFormLoginEnabled(true);
        loginPageFilter.setUsernameParameter("username");
        loginPageFilter.setPasswordParameter("password");
        loginPageFilter.setLoginPageUrl("/login");
        loginPageFilter.setLogoutSuccessUrl("/login?logout");
        loginPageFilter.setFailureUrl("/login?error");
        loginPageFilter.setAuthenticationUrl("/login");

        securityFilters.add(loginPageFilter);
        securityFilters.add(formLoginFilter);
        securityFilters.add(new DefaultLogoutPageGeneratingFilter());


        FilterChainProxy springSecurityFilterChain = new FilterChainProxy(new DefaultSecurityFilterChain(AnyRequestMatcher.INSTANCE, securityFilters));
        registry.registerBean("springSecurityFilterChain", FilterChainProxy.class, spec ->
                spec.supplier(context -> springSecurityFilterChain));
    }

}
