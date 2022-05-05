package demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class OkController {
	@GetMapping("/**")
	String ok() {
		return "ok";
	}
}
