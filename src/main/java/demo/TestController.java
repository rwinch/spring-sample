package demo;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	private Object modelAttribute;

	public void reset() {
		this.modelAttribute = null;
	}

	public Object getModelAttribute() {
		return this.modelAttribute;
	}

	@PostMapping("/single-simple-immutable")
	String singSimpleImmutable(@ModelAttribute SingleSimpleImmutable singleSimpleImmutable) {
		this.modelAttribute = singleSimpleImmutable;
		return "Hi";
	}

	@PostMapping("/multi-simple-immutable")
	String singSimpleImmutable(@ModelAttribute MultiSimpleImmutable multiSimpleImmutable) {
		this.modelAttribute = multiSimpleImmutable;
		return "Hi";
	}

	@PostMapping("/nested-with-no-getter")
	String nestedWithNoGetter(@ModelAttribute NestedWithNoGetter nestedWithNoGetter) {
		this.modelAttribute = nestedWithNoGetter;
		return "Hi";
	}

	@PostMapping("/nested-with-single-simple-immutable")
	String nestedWithSingleSimpleImmutable(@ModelAttribute NestedWithSingleSimpleImmutable nestedWithSingleSimpleImmutable) {
		this.modelAttribute = nestedWithSingleSimpleImmutable;
		return "Hi";
	}
}
