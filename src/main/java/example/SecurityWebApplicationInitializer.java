/*
 * Copyright 2020 the original author or authors.
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

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.util.Assert;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;

public class SecurityWebApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		String filterName = AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME;
		DelegatingFilterProxy springSecurityFilterChain = new DelegatingFilterProxy(filterName);
		registerFilter(servletContext, filterName, springSecurityFilterChain);
	}

	private void registerFilter(ServletContext servletContext, String filterName,
								Filter filter) {
		FilterRegistration.Dynamic registration = servletContext.addFilter(filterName, filter);
		Assert.state(registration != null, () -> "Duplicate Filter registration for '" + filterName
				+ "'. Check to ensure the Filter is only configured once.");
		EnumSet<DispatcherType> dispatcherTypes = getSecurityDispatcherTypes();
		registration.addMappingForUrlPatterns(dispatcherTypes, true, "/springpath/*");
	}

	/**
	 * Get the {@link DispatcherType} for the springSecurityFilterChain.
	 * @return
	 */
	protected EnumSet<DispatcherType> getSecurityDispatcherTypes() {
		return EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR, DispatcherType.ASYNC);
	}
}
