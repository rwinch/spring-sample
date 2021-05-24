package demo.getters;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class GettersTestControllerTests {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	private GettersTestController testController;

	@AfterEach
	void reset() {
		this.testController.reset();
	}

	@Test
	void createPoint() throws Exception {
		Point expected = new Point(1,2);
		MockHttpServletRequestBuilder request = post("/getters/point")
				.param("x", Integer.toString(expected.getX()))
				.param("y", Integer.toString(expected.getY()));

		this.mockMvc.perform(request)
				.andExpect(status().isOk());
		assertThat(this.testController.getModelAttribute()).usingRecursiveComparison().isEqualTo(expected);
	}

	/**
	 * Since Line has getters for Point classes, it will try and autogrow the property and fail due to missing
	 * default constructor
	 */
	@Test
	void createLine() throws Exception {
		Point point1 = new Point(1,2);
		Point point2 = new Point(4,5);
		String description = "A line";
		Line expected = new Line("A line", point1, point2);
		MockHttpServletRequestBuilder request = post("/getters/line")
				.param("description", description)
				.param("point1.x", Integer.toString(point1.getX()))
				.param("point1.y", Integer.toString(point1.getY()))
				.param("point2.x", Integer.toString(point2.getX()))
				.param("point2.y", Integer.toString(point2.getY()));

		this.mockMvc.perform(request)
				.andExpect(status().isOk());
		assertThat(this.testController.getModelAttribute()).usingRecursiveComparison().isEqualTo(expected);
	}

}
