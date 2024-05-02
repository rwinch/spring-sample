package example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MessageController {

    @GetMapping("/")
    String message() {
        return "Hello, World!";
    }
}
