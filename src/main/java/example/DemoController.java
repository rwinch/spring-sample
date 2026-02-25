package example;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DemoController {

    // does not care the authorities but must be authenticated
    @GetMapping("/authenticated")
    String authenticated() {
        return "authenticated";
    }

    // public but has different logic if logged in...users likely want to be able to invoke both flows
    @GetMapping("/permitAll")
    String message(Principal p) {
        if (p == null) {
            return "Hello, World!";
        }
        return "Hello, " + p.getName();
    }

    @GetMapping("/admin")
    String admin(Principal p) {
        return p.getName() + " is an admin";
    }

    @GetMapping("/hasAnyRole")
    String hasAnyRoleADMINUSSER(Principal p) {
        return p.getName() + " is an admin or user";
    }

    @GetMapping("/user")
    String user(Principal p) {
        return p.getName() + " is a regular user";
    }
}
