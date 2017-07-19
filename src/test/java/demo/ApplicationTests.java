package demo;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import demo.pcf.ClientCertificateMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ApplicationTests {

	@Autowired
	MockMvc mockMvc;

	static final String HEADER = "X-Forwarded-Client-Cert";

	private static final String CERTIFICATE_1 = "" +
			"MIIDLTCCAhWgAwIBAgIkMDg3ZjVmZGMtOThkNy00MGMwLTY0ZDMtZmQ5NWFmODMx" +
			"OThkMA0GCSqGSIb3DQEBCwUAMBoxGDAWBgNVBAMMD2NyZWRodWJDbGllbnRDQTAe" +
			"Fw0xNzA1MDIwMDQ5MzFaFw0xNzA1MDMwMDQ5MzFaMGIxMTAvBgNVBAsTKGFwcDoy" +
			"MzI4MmZkMS0zNWI0LTQ1ZGQtYTYwMi04Zjc2ZjRhNjBkMTExLTArBgNVBAMTJDA4" +
			"N2Y1ZmRjLTk4ZDctNDBjMC02NGQzLWZkOTVhZjgzMTk4ZDCCASIwDQYJKoZIhvcN" +
			"AQEBBQADggEPADCCAQoCggEBAPhcSn56pIVWI0RpwrkC3WcvumLw+3i/oj3YBbEx" +
			"AUAFJMFl/yt1zpAghLvYOOiiUS/W04SKp8Z9FHlmNabJOzV40RIciSbYCW0tBeFG" +
			"KNkgolTGamvRLZkkHUJdywEQkvnMG7+2XczDBoCZ7fdBepg6gieSqGhQwl/sO7x/" +
			"TouvQnujKwJLiXOKQq00TkT+MVEzOZyOMlqFh9r2XjUGuh1HnRM0IAj6buR5663t" +
			"4lAQqOluTAVNCKWSrAMIKb0G4QPTQ4pKRTeMEnTijFErtKlpzc64HYrBpufj1K/q" +
			"TxYIy3EgeT3UVSclSub14M4/r/mOmWotYP81BR1Ko7pxV28CAwEAAaMTMBEwDwYD" +
			"VR0RBAgwBocECv4AAjANBgkqhkiG9w0BAQsFAAOCAQEAuG8A33+Un2rvXA+qAf40" +
			"gBponN2mjx0drasw/MqBnclUL1MYvOepqcGxxNB/1Ok/bKKDMr03ugVaxzAdoknA" +
			"NwIyY/ghL6xHs/JrmuSGDs9BeNF0y8TOpQmmjh1EDFtR9YFuTRP1OZ6XBf5fbd80" +
			"Q684k/Wu8ELywZJd53FKcTPJRQ/Yjn4QFJORtcNFlvMFWTmJLLiMDbI8JBcqMLZH" +
			"sgdyBtV7kJdZU3nszgFEPspYzFfxQZmq6V+pJb+dmG2jYWrX/R21J9x1dJHBCoPp" +
			"XcqQm8pYsDxi+HTGS6an78sHqrvU5uQJq2MW8o6iBJR80bFgWSl7GTqK3Xz5iTxU" +
			"Ew==";

	@Test
	public void noHeader() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isForbidden())
				.andExpect(unauthenticated())
				.andExpect(content().string(""));
	}


	@Test
	public void header() throws Exception {
		mockMvc.perform(get("/").header(HEADER, CERTIFICATE_1))
				.andExpect(status().isOk())
				.andExpect(authenticated().withUsername("23282fd1-35b4-45dd-a602-8f76f4a60d11"))
				.andExpect(content().string("Hello X509!"));
	}

}
