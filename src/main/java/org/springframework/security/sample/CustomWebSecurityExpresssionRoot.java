/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.springframework.security.sample;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

/**
 * @author Rob Winch
 */
public class CustomWebSecurityExpresssionRoot extends WebSecurityExpressionRoot {
    public CustomWebSecurityExpresssionRoot(Authentication a, FilterInvocation fi) {
        super(a, fi);
    }

    public boolean uiAuthz(String uiElement, String val) {
        return uiElement != null && val != null && uiElement.equals(val);
    }
}
