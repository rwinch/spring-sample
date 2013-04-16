/*
 * Copyright 2002-2013 the original author or authors.
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
package org.springframework.security;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.util.Assert;

/**
 * @author Rob Winch
 *
 */
public class WebSecurityConfigurationSupport {

    @Bean
    public FilterChainProxy springSecurityFilterChain() {
        Assert.notNull(getCustomFilter(), "custom filter is required");
        return new FilterChainProxy();
    }

    @Bean
    @DependsOn("springSecurityFilterChain")
    public AuthenticationManager authenticationManager() {
        AuthenticationManager authenticationManager = getAuthenticationManager();
        Assert.notNull(authenticationManager, "AuthenticationManager cannot be null");
        return authenticationManager;
    }

    protected Filter getCustomFilter() {
        return null;
    }

    protected AuthenticationManager getAuthenticationManager() {
        return null;
    }
}
