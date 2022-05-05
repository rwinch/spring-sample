package demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class OkController {
	@RequestMapping("/**")
	String ok() {
		return "ok";
	}
}
