package demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Rob Winch
 */
@Controller
public class LoginController {
	@GetMapping("/login")
	String login() {
		return "login";
	}
}
