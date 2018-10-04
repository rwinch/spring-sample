package demo;

import io.reactivex.Single;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import reactor.adapter.rxjava.RxJava2Adapter;
import reactor.core.publisher.Mono;

/**
 * A service written by another team that requires RxJava
 * @author Rob Winch
 */
@Component
public class ReactorMessageService {

	public Mono<String> message() {
		return ReactiveSecurityContextHolder.getContext()
			.map(SecurityContext::getAuthentication)
			.map(Authentication::getName)
			.map(n -> "Hello " + n);
	}
}
