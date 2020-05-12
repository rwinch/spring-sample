package demo;

import java.security.Principal;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jwt.JWTClaimsSet;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rob Winch
 */
@RestController
public class JwtController {
	final JWSSigner signer;

	public JwtController(JWSSigner signer) {
		this.signer = signer;
	}

	@PostMapping("/token")
	String token(Principal principal) throws Exception {
		Instant now = Instant.now();
		JWTClaimsSet claims = new JWTClaimsSet.Builder()
				.issuer("http://localhost:8080")
				.audience("http://localhost:8080")
				.expirationTime(Date.from(now.plus(Duration.ofDays(2))))
				.issueTime(Date.from(now))
				.subject(principal.getName())
				.build();
		JWSObject jwsObject = new JWSObject(
				new JWSHeader.Builder(JWSAlgorithm.RS256).build(),
				new Payload(claims.toJSONObject()));

		jwsObject.sign(this.signer);

		return jwsObject.serialize();

	}
}
