package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTests {
	@Autowired
	MockMvc mockMvc;

	@Test
	public void entityExplicitHeaders() throws Exception {
		this.mockMvc.perform(get("/entity/explicit"))
				.andExpect(header().string(HttpHeaders.CACHE_CONTROL, "private, max-age=1800, must-revalidate"))
				.andExpect(header().doesNotExist(HttpHeaders.EXPIRES))
				.andExpect(header().doesNotExist(HttpHeaders.PRAGMA));
	}


	@Test
	public void entitySecurityHeaders() throws Exception {
		this.mockMvc.perform(get("/entity/security"))
				.andExpect(header().string(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, max-age=0, must-revalidate"))
				.andExpect(header().string(HttpHeaders.EXPIRES, "0"))
				.andExpect(header().string(HttpHeaders.PRAGMA, "no-cache"));
	}
}
