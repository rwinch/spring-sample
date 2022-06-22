package example;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SessionAccessedFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		filterChain.doFilter(new HttpServletRequestWrapper(request) {
			@Override
			public HttpSession getSession(boolean create) {
				new RuntimeException("getSession(" + create +")").printStackTrace();
				return super.getSession(create);
			}

			@Override
			public HttpSession getSession() {
				new RuntimeException("getSession()").printStackTrace();
				return super.getSession();
			}
		}, response);
	}
}
