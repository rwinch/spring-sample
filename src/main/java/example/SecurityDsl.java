package example;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.Filter;

import org.springframework.beans.factory.BeanRegistrar;
import org.springframework.beans.factory.BeanRegistry;
import org.springframework.core.env.Environment;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class SecurityDsl implements BeanRegistrar {

    public void register(BeanRegistry registry, Environment env) {
        List<Filter> securityFilters = new ArrayList<>();


        FilterChainProxy springSecurityFilterChain = new FilterChainProxy(new DefaultSecurityFilterChain(AnyRequestMatcher.INSTANCE, securityFilters));
        registry.registerBean("springSecurityFilterChain", FilterChainProxy.class, spec ->
                spec.supplier(context -> springSecurityFilterChain));
    }

}
