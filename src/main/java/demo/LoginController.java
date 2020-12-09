package demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/admin_login")
	String adminLogin() {
		return "admin/login";
	}

	@GetMapping("/user_login")
	String login() {
		return "login";
	}
}
