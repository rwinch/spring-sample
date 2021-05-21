/*
 * Copyright 2021 the original author or authors.
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

package demo;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Controller for
 */
@Controller
@RequestMapping("projects/survey")
class ProjectSurveyController {


	@GetMapping("form")
	String form(@ModelAttribute ProjectSurvey projectSurvey) {
		return "project/survey/form";
	}

	@PostMapping
	String post(@Valid ProjectSurvey projectSurvey, Errors errors) {
		if (errors.hasErrors()) {
			return "project/form";
		}
		return "redirect:/projects/survey/form?success";
	}

	@ModelAttribute("success")
	boolean success(@RequestParam(required = false) String success) {
		return success != null;
	}
}
