package example;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MessageController {

    @GetMapping("/")
    String message(Principal p) {
        return "Hello, " + p.getName();
    }
}
