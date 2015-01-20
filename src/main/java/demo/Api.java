package demo;

import org.springframework.security.access.prepost.PreAuthorize;

public interface Api {

	@PreAuthorize("denyAll")
	void denyAll();
}
