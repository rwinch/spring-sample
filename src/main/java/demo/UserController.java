package demo;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@RequestMapping("/{username}/{message}")
	public String sayHi(@PathVariable String username, @PathVariable String message) {
		return "User " + username + " says "+ message;
	}
}
