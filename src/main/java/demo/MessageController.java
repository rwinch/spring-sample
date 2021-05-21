package demo;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/message")
public class MessageController {
    @GetMapping("form")
    String message(@ModelAttribute Message message) {
        return "message/form";
    }

    @PostMapping
    String save(@Valid Message message, Errors errors) {
        return "redirect:/message/form?success";
    }
}
