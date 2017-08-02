package demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Rob Winch
 * @since 5.0
 */
@Controller
public class IndexController {
	@GetMapping("/")
	public String index() {
		return "/index";
	}
}
