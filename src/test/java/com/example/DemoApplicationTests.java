package com.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DemoApplicationTests {
	@Autowired
	MockMvc mockMvc;

	@Test
	public void redirectXForwardedHost() throws Exception {
		mockMvc.perform(get("/context/").contextPath("/context").header("X-Forwarded-Host", "example.com"))
			.andExpect(redirectedUrl("http://example.com/context/login"));
	}

	@Test
	public void redirectXForwardedPrefix() throws Exception {
		mockMvc.perform(get("/context/").contextPath("/context").header("X-Forwarded-Host", "example.com").header("X-Forwarded-Prefix", "/new"))
			.andExpect(redirectedUrl("http://example.com/new/login"));
	}
}
