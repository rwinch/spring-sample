package example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MessageController {

    @GetMapping("/api/v1/foo")
    String foo() {
        return "foo";
    }

    @GetMapping("/api/v1/bar")
    String bar() {
        return "bar";
    }

    @GetMapping("/api/v1/baz")
    String baz() {
        return "baz";
    }
}
