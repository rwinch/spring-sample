package demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;

//@RestController
public class SessionController {

    @RequestMapping("authenticate")
    public Map<String,String> authenticate(@AuthenticationPrincipal CustomUser principal) {
        Map<String,String> user = new HashMap<String,String>();
        user.put("username", principal.getUsername());
        return user;
    }
}
