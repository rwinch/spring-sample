/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package demo;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
public class MessageController {

	@GetMapping("/")
	public String message() {
		return "Hello Boot!";
	}

	@GetMapping("/principal")
	public String principal(Principal principal) {
		return "Hello " + principal.getName();
	}

	@GetMapping("/principal/mono")
	public Mono<String> principalMono(Mono<Principal> principal) {
		return principal.map( p -> "Hello " + p.getName());
	}

	@GetMapping("/userdetails")
	public String principal(@AuthenticationPrincipal UserDetails userDetails) {
		return "Hello " + userDetails.getName();
	}

	@GetMapping("/userdetails/mono")
	public Mono<String> userDetailsMono(@AuthenticationPrincipal Mono<UserDetails> userDetails) {
		return userDetails.map( p -> "Hello " + p.getName());
	}

	@GetMapping("/exchange/principal")
	public Mono<String> exchangePrincipal(ServerWebExchange exchange) {
		return exchange.getPrincipal().map( p -> "Hello " + p.getName());
	}
}
