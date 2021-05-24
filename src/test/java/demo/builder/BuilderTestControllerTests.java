package demo.builder;

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
class BuilderTestControllerTests {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	private BuilderTestController testController;

	@AfterEach
	void reset() {
		this.testController.reset();
	}

	@Test
	void createPointWhenDescription() throws Exception {
		Point expected = Point.builder()
				.x(1)
				.y(2)
				.build();
		MockHttpServletRequestBuilder request = post("/primary/point")
				.param("x", Integer.toString(expected.getX()))
				.param("y", Integer.toString(expected.getY()));

		this.mockMvc.perform(request)
				.andExpect(status().isOk());
		assertThat(this.testController.getModelAttribute()).usingRecursiveComparison().isEqualTo(expected);
	}
}
