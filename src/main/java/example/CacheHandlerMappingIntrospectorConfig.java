package example;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.EnumSet;

@Configuration
public class CacheHandlerMappingIntrospectorConfig {
//	@Bean
	FilterRegistrationBean<Filter> cacheHandlermappingInterospectorFilter(HandlerMappingIntrospector hmi) {
		Filter cacheFilter = hmi.createCacheFilter();
		FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>(cacheFilter);
		registrationBean.setOrder(Ordered.LOWEST_PRECEDENCE);
		registrationBean.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
		return registrationBean;
	}
}
