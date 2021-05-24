package demo.getters;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/getters")
public class GettersTestController {

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

	@PostMapping("/line")
	String line(@ModelAttribute Line line) {
		this.modelAttribute = line;
		return "Hi";
	}
}
