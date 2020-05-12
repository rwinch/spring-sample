package demo;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Rob Winch
 */
@Configuration
public class NimbusConfig {
	@Bean
	RSAKey rsaKey() throws Exception {
		return new RSAKeyGenerator(2048)
				.keyID("123")
				.generate();
	}

	@Bean
	RSASSASigner rsassaSigner(RSAKey rsaKey) throws JOSEException {
		return new RSASSASigner(rsaKey);
	}
}
