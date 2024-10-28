package example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;

@Configuration(proxyBeanMethods = false)
public class WorkAroundConfig {

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	WebFilter writeableHeaders() {
		return (exchange, chain) -> {
			HttpHeaders writeableHeaders = HttpHeaders.writableHttpHeaders(
					exchange.getRequest().getHeaders());
			ServerHttpRequestDecorator writeableRequest = new ServerHttpRequestDecorator(
					exchange.getRequest()) {
				@Override
				public HttpHeaders getHeaders() {
					return writeableHeaders;
				}
			};
			ServerWebExchange writeableExchange = exchange.mutate()
				.request(writeableRequest)
				.build();
			return chain.filter(writeableExchange);
		};
	}
}
