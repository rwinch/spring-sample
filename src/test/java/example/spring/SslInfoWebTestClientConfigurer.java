package example.spring;

import java.security.cert.X509Certificate;

import reactor.core.publisher.Mono;

import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.SslInfo;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClientConfigurer;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

class SslInfoWebTestClientConfigurer {

	static WebTestClientConfigurer sslInfo(SslInfo sslInfo) {
		return (builder, httpHandlerBuilder, connector) -> {
			builder.apply(new WebTestClientConfigurer() {
				@Override
				public void afterConfigurerAdded(WebTestClient.Builder builder,
						WebHttpHandlerBuilder httpHandlerBuilder,
						ClientHttpConnector connector) {
					httpHandlerBuilder.filters((filters) -> filters.add(0, new SslInfoOverrideWebFilter(sslInfo)));
				}
			});
		};
	}

	static WebTestClientConfigurer x509(X509Certificate... certificates) {
		return sslInfo(SslInfo.from("123", certificates));
	}

	private static class SslInfoOverrideWebFilter implements WebFilter {
		private final SslInfo sslInfo;

		private SslInfoOverrideWebFilter(SslInfo sslInfo) {
			this.sslInfo = sslInfo;
		}

		@Override
		public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
			ServerHttpRequest sslInfoRequest = exchange.getRequest().mutate().sslInfo(sslInfo)
					.build();
			ServerWebExchange sslInfoExchange = exchange.mutate().request(sslInfoRequest).build();
			return chain.filter(sslInfoExchange);
		}
	}
}
