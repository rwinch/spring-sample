package example.spring;

import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.springframework.core.io.ClassPathResource;

public class TestCertificates {

	static X509Certificate rod() {
		return loadCert("rod.cer");
	}

	private static <T extends Certificate> T loadCert(String location) {
		try (InputStream is = new ClassPathResource(location).getInputStream()) {
			CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
			return (T) certFactory.generateCertificate(is);
		}
		catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}
}
