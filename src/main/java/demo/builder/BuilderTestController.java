package demo.builder;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/builder")
class BuilderTestController {

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
