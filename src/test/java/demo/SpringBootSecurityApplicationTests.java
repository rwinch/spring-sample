package demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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

@WebMvcTest
public class SpringBootSecurityApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void formIsOk() throws  Exception{
		this.mockMvc.perform(get("/projects/survey/form"))
				.andExpect(status().isOk());
	}


	@Test
	void postIsSuccess() throws  Exception{
		MockHttpServletRequestBuilder request = post("/projects/survey")
				.param("id", "survey-id")


				.param("project.organization","spring-projects")
//				.param("project.repository", "spring-security")
//				.param("project.name", "Spring Security")

//				.param("projectProfile.id","profile-id")
//				.param("projectProfile.project.organization","spring-projects")
//				.param("projectProfile.project.repository", "spring-security")
//				.param("projectProfile.project.name", "Spring Security")
//				.param("projectProfile.project.teamMembers", "rwinch,jgrandja")
//				.param("projectProfile.projectLeadName", "Rob Winch")
//				.param("projectProfile.lifeCycleStage", "ACTIVE_DEVELOPMENT")
//				.param("projectProfile.commercialAlignment", "BUSINESS_GROWING")
//				.param("projectProfile.commerciallySupported", "true")
				;
		this.mockMvc.perform(request)
				.andExpect(redirectedUrl("/projects/survey/form?success"))
				.andExpect(status().is3xxRedirection());
	}
}
