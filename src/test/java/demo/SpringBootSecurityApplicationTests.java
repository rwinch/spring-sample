package demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
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
				.param("projectSurvey.name.firstName", "Rob")
				.param("projectSurvey.name.lastName", "Winch")
				.param("projectSurvey.text", "Hello");
		this.mockMvc.perform(request)
				.andExpect(redirectedUrl("/message/form?success"))
				.andExpect(status().is3xxRedirection());
	}
}
