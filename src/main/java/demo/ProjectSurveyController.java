package demo;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("projects/survey")
class ProjectSurveyController {


    @GetMapping("form")
    String form(@ModelAttribute ProjectSurvey projectSurvey) {
        return "project/survey/form";
    }

    @PostMapping
    String post(@ModelAttribute ProjectSurvey projectSurvey, Errors errors) {
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
