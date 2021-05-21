package demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/name")
class NameController {
	@GetMapping("form")
	String form(@ModelAttribute Name name) {
		return "name/form";
	}

	@PostMapping
	String createName(@ModelAttribute Name name) {
		return "redirect:/name/form?success";
	}
}
