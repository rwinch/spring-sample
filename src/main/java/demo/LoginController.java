package demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/admin/login")
	String adminLogin() {
		return "admin/login";
	}

	@GetMapping("/login")
	String login() {
		return "login";
	}
}
