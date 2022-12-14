package example;

import java.util.List;

import example.ProgramaticMemoryRealm.UserInformation;
import org.apache.catalina.Context;
import org.apache.catalina.authenticator.BasicAuthenticator;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.realm.RealmBase;
import org.apache.tomcat.util.descriptor.web.LoginConfig;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Rob Winch
 */
@Configuration
public class ContainerSecurityConfiguration {

	@Bean
	public TomcatServletWebServerFactory servletContainer() {
		return new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				context.getPipeline().addValve(new BasicAuthenticator());
				context.setRealm(createRealm());
				context.setLoginConfig(createLoginConfig());
				context.addSecurityRole("ROLE_USER");
				context.addConstraint(createSecurityConstraint());
			}

			private static RealmBase createRealm() {
				GenericPrincipal user = new GenericPrincipal("user", "password", List.of("ROLE_USER"));
				return new ProgramaticMemoryRealm(new UserInformation(user, "password"));
			}

			private static RealmBase createXmlRealm() {
				GenericPrincipal user = new GenericPrincipal("user", "password", List.of("ROLE_USER"));
				return new ProgramaticMemoryRealm(new UserInformation(user, "password"));
			}

			private static LoginConfig createLoginConfig() {
				LoginConfig config = new LoginConfig();
				config.setRealmName("basic");
				config.setAuthMethod("BASIC");
				return config;
			}

			private static SecurityConstraint createSecurityConstraint() {
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setDisplayName("Private resources");
				SecurityCollection securityCollection = new SecurityCollection();
				securityCollection.addPattern("/private/*");
				securityConstraint.addCollection(securityCollection);
				securityConstraint.addAuthRole("ROLE_USER");
				return securityConstraint;
			}
		};
	}

}
