package demo;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.intercept.aopalliance.MethodSecurityInterceptor;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.util.SimpleMethodInvocation;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThatCode;


@SpringBootTest
public class ApplicationTests {

	@Autowired
	MethodSecurityInterceptor interceptor;

	@Autowired
	MessageService messages;

	@Test
	@WithMockUser
	public void peekMessageWhenUserThenAccessDenied() throws Throwable {
		MethodInvocation invocation = new SimpleProceedMethodInvocation(this.messages, this.messages.getClass().getMethod("getMessage"));

		assertThatCode(() -> this.interceptor.invoke(invocation)).isInstanceOf(AccessDeniedException.class);
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void peekMessageWhenAdminTthenGranted() throws Throwable {
		MethodInvocation invocation = new SimpleProceedMethodInvocation(this.messages, this.messages.getClass().getMethod("getMessage"));

		assertThatCode(() -> this.interceptor.invoke(invocation)).doesNotThrowAnyException();
	}

	static class SimpleProceedMethodInvocation extends SimpleMethodInvocation {
		private final Object proceed;

		public SimpleProceedMethodInvocation(Object proceed, Object targetObject, Method method, Object... arguments) {
			super(targetObject, method, arguments);
			this.proceed = proceed;
		}

		public SimpleProceedMethodInvocation(Object targetObject, Method method, Object... arguments) {
			this(null, targetObject, method, arguments);
		}

		@Override
		public Object proceed() {
			return this.proceed;
		}
	}
}
