package example;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface RepositoryService {
	@GetExchange("/repos/{owner}/{repo}")
	Repository getRepository(@PathVariable String owner, @PathVariable String repo);
}
