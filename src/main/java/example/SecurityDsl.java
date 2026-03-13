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

        FilterChainProxy springSecurityFilterChain = new FilterChainProxy(new DefaultSecurityFilterChain(AnyRequestMatcher.INSTANCE, securityFilters));
        registry.registerBean("springSecurityFilterChain", FilterChainProxy.class, spec ->
                spec.supplier(context -> springSecurityFilterChain));
    }

}
