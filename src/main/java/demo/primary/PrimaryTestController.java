package demo.primary;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/primary")
class PrimaryTestController {

	private Object modelAttribute;

	public void reset() {
		this.modelAttribute = null;
	}

	public Object getModelAttribute() {
		return this.modelAttribute;
	}

	@PostMapping("/point")
	String point(@ModelAttribute Point point) {
		this.modelAttribute = point;
		return "Hi";
	}

}
