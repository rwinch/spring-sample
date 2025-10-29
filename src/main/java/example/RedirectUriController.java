package example;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * In many Single Sign On flows, the application needs to redirect to an IdP with a
 * parameter that indicates where the IdP should redirect back to after authentication.
 * In OAuth2 that parameter is the redirect_uri and must be validated by the IdP.
 */
@Controller
public class RedirectUriController {
	@GetMapping("/**")
	String oauth2Login(HttpServletRequest request) {
		String redirectUri = ServletUriComponentsBuilder.fromRequest(request)
			.path("/oauth2/login")
			.toUriString();
		return "redirect:http://localhost/oauth2/authorize?redirect_uri=" + redirectUri;
	}
}
