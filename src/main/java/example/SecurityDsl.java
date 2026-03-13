package example;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.Filter;

import org.springframework.beans.factory.BeanRegistrar;
import org.springframework.beans.factory.BeanRegistry;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter;
import org.springframework.security.web.authentication.ui.DefaultResourcesFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.web.header.writers.CacheControlHeadersWriter;
import org.springframework.security.web.header.writers.HstsHeaderWriter;
import org.springframework.security.web.header.writers.XContentTypeOptionsHeaderWriter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.access.intercept.RequestMatcherDelegatingAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

public class SecurityDsl implements BeanRegistrar {

    public void register(BeanRegistry registry, Environment env) {
        List<Filter> securityFilters = new ArrayList<>();
        
        SecurityContextRepository securityContextRepository = securityContextRepository();

        securityFilters.add(headerWriterFilter());
        securityFilters.add(securityContextHolderFilter(securityContextRepository));
        securityFilters.add(securityContextHolderAwareRequestFilter());
        securityFilters.add(defaultResourcesFilter());
        securityFilters.add(loginPageGeneratingFilter());
        securityFilters.add(usernamePasswordAuthenticationFilter(authenticationManager(), securityContextRepository));
        securityFilters.add(logoutPageGeneratingFilter());
        securityFilters.add(logoutFilter());
        securityFilters.add(exceptionTranslationFilter());
        securityFilters.add(authorizationFilter());

        FilterChainProxy springSecurityFilterChain = new FilterChainProxy(new DefaultSecurityFilterChain(AnyRequestMatcher.INSTANCE, securityFilters));
        registry.registerBean("springSecurityFilterChain", FilterChainProxy.class, spec ->
                spec.supplier(context -> springSecurityFilterChain));
    }

    private SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }

    private AuthenticationManager authenticationManager() {
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager(
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build()
        );
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        return new ProviderManager(authenticationProvider);
    }

    private HeaderWriterFilter headerWriterFilter() {
        return new HeaderWriterFilter(List.of(
            new XContentTypeOptionsHeaderWriter(),
            new XXssProtectionHeaderWriter(),
            new CacheControlHeadersWriter(),
            new HstsHeaderWriter(),
            new XFrameOptionsHeaderWriter()
        ));
    }

    private SecurityContextHolderFilter securityContextHolderFilter(SecurityContextRepository securityContextRepository) {
        return new SecurityContextHolderFilter(securityContextRepository);
    }

    private SecurityContextHolderAwareRequestFilter securityContextHolderAwareRequestFilter() {
        SecurityContextHolderAwareRequestFilter requestAwareFilter = new SecurityContextHolderAwareRequestFilter();
        try {
            requestAwareFilter.afterPropertiesSet();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return requestAwareFilter;
    }

    private DefaultResourcesFilter defaultResourcesFilter() {
        return DefaultResourcesFilter.css();
    }

    private DefaultLoginPageGeneratingFilter loginPageGeneratingFilter() {
        DefaultLoginPageGeneratingFilter loginPageFilter = new DefaultLoginPageGeneratingFilter();
        loginPageFilter.setFormLoginEnabled(true);
        loginPageFilter.setUsernameParameter("username");
        loginPageFilter.setPasswordParameter("password");
        loginPageFilter.setLoginPageUrl("/login");
        loginPageFilter.setLogoutSuccessUrl("/login?logout");
        loginPageFilter.setFailureUrl("/login?error");
        loginPageFilter.setAuthenticationUrl("/login");
        return loginPageFilter;
    }

    private UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
        UsernamePasswordAuthenticationFilter formLoginFilter = new UsernamePasswordAuthenticationFilter(authenticationManager);
        formLoginFilter.setSecurityContextRepository(securityContextRepository);
        formLoginFilter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error"));
        return formLoginFilter;
    }

    private DefaultLogoutPageGeneratingFilter logoutPageGeneratingFilter() {
        return new DefaultLogoutPageGeneratingFilter();
    }

    private LogoutFilter logoutFilter() {
        return new LogoutFilter("/login?logout", new SecurityContextLogoutHandler());
    }

    private ExceptionTranslationFilter exceptionTranslationFilter() {
        return new ExceptionTranslationFilter(new LoginUrlAuthenticationEntryPoint("/login"));
    }

    private AuthorizationFilter authorizationFilter() {
        AuthorizationManager<jakarta.servlet.http.HttpServletRequest> authorizationManager = RequestMatcherDelegatingAuthorizationManager.builder()
                .requestMatchers(PathPatternRequestMatcher.pathPattern("/default-ui.css")).permitAll()
                .requestMatchers(PathPatternRequestMatcher.pathPattern("/login")).permitAll()
                .requestMatchers(PathPatternRequestMatcher.pathPattern("/logout")).permitAll()
                .anyRequest().authenticated()
                .build();
        return new AuthorizationFilter(authorizationManager);
    }

}
