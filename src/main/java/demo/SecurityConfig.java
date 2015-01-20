package demo;

/**
 * <p>Created: Jan 15, 2015</p>
 * @author Alexander S. Pogrebnyak
 */

///@name Imports
//@{

import java.lang.reflect.Method;

import javax.sql.DataSource;

import org.aopalliance.aop.Advice;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

//@}

/**
 * Security config to reproduce SEC-2815
 *
 * @author Alexander S. Pogrebnyak
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig {

    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    public AbstractPointcutAdvisor metaDataSourceAdvisor() {
    	AbstractPointcutAdvisor methodAdvisor = new AbstractPointcutAdvisor() {

			@Override
			public Advice getAdvice() {
				return new Advice() {
				};
			}

			@Override
			public Pointcut getPointcut() {
				return new Pointcut() {

					@Override
					public MethodMatcher getMethodMatcher() {
						return new MethodMatcher() {

							@Override
							public boolean matches(Method method,
									Class<?> targetClass) {
								// TODO Auto-generated method stub
								return false;
							}

							@Override
							public boolean isRuntime() {
								// TODO Auto-generated method stub
								return false;
							}

							@Override
							public boolean matches(Method method,
									Class<?> targetClass, Object[] args) {
								// TODO Auto-generated method stub
								return false;
							}};
					}

					@Override
					public ClassFilter getClassFilter() {
						return new ClassFilter() {

							@Override
							public boolean matches(Class<?> clazz) {
								// TODO Auto-generated method stub
								return false;
							}
						};
					}
				};
			}
		};
        return methodAdvisor;
    }

    @Autowired(required = false)
    public void setAuthenticationConfiguration(DataSource dataSource) {
    }
}