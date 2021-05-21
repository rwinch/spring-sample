package demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class SpringBootSecurityApplicationTests {
	@Autowired
	MockMvc mockMvc;

	@Test
	void nestedImmutableForm() throws Exception{
		this.mockMvc.perform(get("/projects/survey/form"))
				.andExpect(status().isOk());
	}

	@Test
	void postNestedImmutableForm() throws Exception{
		MockHttpServletRequestBuilder request = post("/projects/survey")
//				.param("projectSurvey.name.firstName", "Rob")
//				.param("projectSurvey.name.lastName", "Winch")
				.param("projectSurvey.id", "Hello");
		MvcResult result = this.mockMvc.perform(request)
				.andExpect(redirectedUrl("/projects/survey/form?success"))
				.andExpect(status().is3xxRedirection())
				.andReturn();


	}
}
