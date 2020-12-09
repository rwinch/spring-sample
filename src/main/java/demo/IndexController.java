package demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	@GetMapping("/")
	String index() {
		return "index";
	}

	@GetMapping("/admin/")
	String adminIndex() {
		return "admin/index";
	}
}
