package demo;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.AuditLogger;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionFactory;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.domain.EhCacheBasedAclCache;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;

/**
 * @author Rob Winch
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AclSecurityConfig {
	@Bean
	DefaultPermissionFactory permissionFactory() {
		return  new DefaultPermissionFactory();
	}

	@Bean
	EhCacheManagerFactoryBean cacheManagerFactory() {
		return new EhCacheManagerFactoryBean();
	}

	@Bean
	EhCacheFactoryBean cache(CacheManager cacheManager) {
		EhCacheFactoryBean factory = new EhCacheFactoryBean();
		factory.setCacheManager(cacheManager);
		factory.setCacheName("aclCache");
		return factory;
	}

	@Bean
	ConsoleAuditLogger auditLogger() {
		return new ConsoleAuditLogger();
	}

	@Bean
	DefaultPermissionGrantingStrategy permissionGrantingStrategy(AuditLogger auditLogger) {
		return new DefaultPermissionGrantingStrategy(auditLogger);
	}

	@Bean
	AclAuthorizationStrategyImpl aclAuthorizationStrategy() {
		return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ACL_ADMIN"));
	}

	@Bean
	EhCacheBasedAclCache aclCache(Ehcache cache, PermissionGrantingStrategy permissionGrantingStrategy, AclAuthorizationStrategy aclAuthorizationStrategy) {
		return new EhCacheBasedAclCache(cache, permissionGrantingStrategy, aclAuthorizationStrategy);
	}

	@Bean
	BasicLookupStrategy lookupStrategy(DataSource dataSource, AclCache aclCache, AclAuthorizationStrategy aclAuthorizationStrategy, AuditLogger auditLogger) {
		return new BasicLookupStrategy(dataSource, aclCache, aclAuthorizationStrategy, auditLogger);
	}

	@Bean
	JdbcMutableAclService aclService(DataSource dataSource, LookupStrategy lookupStrategy, AclCache aclCache) {
		return new JdbcMutableAclService(dataSource, lookupStrategy, aclCache);
	}

	@Bean
	AclPermissionEvaluator permissionEvaluator(AclService aclService) {
		return new AclPermissionEvaluator(aclService);
	}
}
