/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

public class FirewallITests {

	private RestTemplate rest;

	private int port;

	@BeforeEach
	void setup() {
		this.port = Integer.parseInt(System.getProperty("app.httpPort"));
		this.rest = new RestTemplate();
	}

	@Test
	void firewallWhenSpringPathThenEnabled() {
		assertThatExceptionOfType(HttpServerErrorException.InternalServerError.class).
			isThrownBy(() -> this.rest.getForObject("http://localhost:" + this.port +"/springpath/;/", String.class));

		assertThatExceptionOfType(HttpServerErrorException.InternalServerError.class).
				isThrownBy(() -> this.rest.getForObject("http://localhost:" + this.port +"/springpath/foo/;/", String.class));
	}

	@Test
	void firewallWhenNotSpringPathThenNotEnabled() {
		assertThatNoException().
				isThrownBy(() -> this.rest.getForObject("http://localhost:" + this.port +"/bar/;/", String.class));
	}

}
