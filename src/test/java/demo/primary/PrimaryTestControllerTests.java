package demo.primary;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class PrimaryTestControllerTests {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	private PrimaryTestController testController;

	@AfterEach
	void reset() {
		this.testController.reset();
	}

	/**
	 * Fails due to:
	 *
	 * IllegalStateException: No primary or single public constructor found for class demo.primary.Point -
	 * and no default constructor found either
	 *
	 * Would be nice if we could mark a constructor as primary some how. It might be able to determine just based on the
	 * parameters that are present.
	 */
	@Test
	void createPointWhenDescription() throws Exception {
		Point expected = new Point("the description", 1,2);
		MockHttpServletRequestBuilder request = post("/primary/point")
				.param("description", expected.getDescription())
				.param("x", Integer.toString(expected.getX()))
				.param("y", Integer.toString(expected.getY()));

		this.mockMvc.perform(request)
				.andExpect(status().isOk());
		assertThat(this.testController.getModelAttribute()).usingRecursiveComparison().isEqualTo(expected);
	}

	@Test
	void createPointWhenNoDescription() throws Exception {
		Point expected = new Point(1,2);
		MockHttpServletRequestBuilder request = post("/primary/point")
				.param("x", Integer.toString(expected.getX()))
				.param("y", Integer.toString(expected.getY()));

		this.mockMvc.perform(request)
				.andExpect(status().isOk());
		assertThat(this.testController.getModelAttribute()).usingRecursiveComparison().isEqualTo(expected);
	}
}
