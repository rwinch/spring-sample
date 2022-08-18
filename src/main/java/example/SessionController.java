/*
 * Copyright 2002-2015 the original author or authors.
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
package example;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class SessionController {

	@GetMapping("/")
	String index() {
		return "index";
	}

	@ResponseBody
	@PostMapping("/session/attribute")
	public Map<String, Object> setAttribute(HttpSession session,
			@RequestParam String attributeName,
			@RequestParam String attributeValue) {
		session.setAttribute(attributeName, attributeValue);
		return sessionAttributes(session);
	}

	@ResponseBody
	@GetMapping("/session/attributes")
	public Map<String, Object> sessionAttributes(HttpSession session) {
		Map<String, Object> attributes = new HashMap<>();
		Enumeration<String> attributeNames = session.getAttributeNames();
		while (attributeNames.hasMoreElements()) {
			String attrName = attributeNames.nextElement();
			attributes.put(attrName, session.getAttribute(attrName));
		}
		return attributes;
	}

}
