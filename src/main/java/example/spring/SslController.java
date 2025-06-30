package example.spring;

import java.security.cert.X509Certificate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.SslInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class SslController {

	public static final ResponseEntity<String> UNAUTHORIZED = ResponseEntity.status(
			HttpStatus.UNAUTHORIZED).build();

	@GetMapping("/")
	ResponseEntity<String> principalName(ServerHttpRequest request) {
		SslInfo sslInfo = request.getSslInfo();
		if (sslInfo == null) {
			return UNAUTHORIZED;
		}
		X509Certificate[] certs = sslInfo.getPeerCertificates();
		if (certs == null || certs.length == 0) {
			return UNAUTHORIZED;
		}
		String username = certs[0].getSubjectX500Principal().getName();
		if (username == null) {
			return UNAUTHORIZED;
		}
		return ResponseEntity.ok(username);
	}
}
