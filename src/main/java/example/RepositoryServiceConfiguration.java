package example;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration(proxyBeanMethods = false)
public class RepositoryServiceConfiguration {
	@Bean
	RepositoryService repositoryService(RestTemplateBuilder restTemplateBldr) {
		RestClient restClient = RestClient.builder(restTemplateBldr.build())
				.baseUrl("http://localhost:8181")
				.build();
		RestClientAdapter adapter = RestClientAdapter.create(restClient);
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter)
				.build();

		return factory.createClient(RepositoryService.class);
	}
}
