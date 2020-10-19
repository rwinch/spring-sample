package example.demo;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class IndexController {
	@GetMapping("/")
	String index() {
		return "index";
	}

	@ModelAttribute
	Authentication authentication(Authentication authentication) {
		return authentication;
	}
}
