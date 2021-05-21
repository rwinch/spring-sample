package demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ImmutableModelAttributeTests {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	private TestController testController;

	@AfterEach
	void reset() {
		this.testController.reset();
	}

	@Test
	void singleSimpleImmutable() throws Exception {
		SingleSimpleImmutable expected = new SingleSimpleImmutable("testing-this");
		MockHttpServletRequestBuilder request = post("/single-simple-immutable")
				.param("id", expected.getId());

		this.mockMvc.perform(request)
				.andExpect(status().isOk());
		assertThat(this.testController.getModelAttribute()).usingRecursiveComparison().isEqualTo(expected);
	}

	@Test
	void multiSimpleImmutable() throws Exception {
		MultiSimpleImmutable expected = new MultiSimpleImmutable("id", "description");
		MockHttpServletRequestBuilder request = post("/multi-simple-immutable")
				.param("id", expected.getId())
				.param("description", expected.getDescription());

		this.mockMvc.perform(request)
				.andExpect(status().isOk());
		assertThat(this.testController.getModelAttribute()).usingRecursiveComparison().isEqualTo(expected);
	}

	@Test
	void nestedWithSingleImmutable() throws Exception {
		SingleSimpleImmutable simpleImmutable = new SingleSimpleImmutable("testing-this");
		NestedWithSingleSimpleImmutable expected = new NestedWithSingleSimpleImmutable(simpleImmutable);
		MockHttpServletRequestBuilder request = post("/nested-with-single-simple-immutable")
				.param("singleSimpleImmutable.id", expected.getSingleSimpleImmutable().getId());

		this.mockMvc.perform(request)
				.andExpect(status().isOk());
		assertThat(this.testController.getModelAttribute()).usingRecursiveComparison().isEqualTo(expected);
	}

	@Test
	void nestedWithNoGetter() throws Exception {
		String id = "testing-this";
		NestedWithNoGetter expected = new NestedWithNoGetter(new SingleSimpleImmutable(id));
		MockHttpServletRequestBuilder request = post("/nested-with-no-getter")
				.param("singleSimpleImmutable.id", id);

		this.mockMvc.perform(request)
				.andExpect(status().isOk());
		assertThat(this.testController.getModelAttribute()).usingRecursiveComparison().isEqualTo(expected);
	}

}
