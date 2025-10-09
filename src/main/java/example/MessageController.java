package example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MessageController {

    @GetMapping("/")
    Message writeWithMvc() {
        return new Message();
    }

    @PostMapping("/")
    void readWithMvc(Message pojo) {
        System.out.println(pojo);
    }
}
