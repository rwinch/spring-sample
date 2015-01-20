package demo;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

//@Configuration
public class PatchSec2822Config {

    @Bean
    public GlobalAuthenticationConfigurerAdapter longerEnableGlobalAuthenticationAutowiredConfigurer(ApplicationContext context) {
        return new EnableGlobalAuthenticationAutowiredConfigurer(context);
    }

    private static class EnableGlobalAuthenticationAutowiredConfigurer extends GlobalAuthenticationConfigurerAdapter {
        private final ApplicationContext context;
        private static final Log logger = LogFactory.getLog(EnableGlobalAuthenticationAutowiredConfigurer.class);

        public EnableGlobalAuthenticationAutowiredConfigurer(ApplicationContext context) {
            this.context = context;
        }

        @Override
        public void init(AuthenticationManagerBuilder auth) {
            Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(EnableGlobalAuthentication.class);
            if(logger.isDebugEnabled()) {
                logger.debug("Eagerly initializing " + beansWithAnnotation);
            }
        }
    }
}
