package org.springframework.security.sample;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

    private static final String USERNAME_ATTR = "username";

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(HttpSession session, Model model) {
        return "home";
    }

    @RequestMapping(value = "writeSession")
    public String writeSession(HttpServletRequest request) {
      request.getSession().setAttribute(USERNAME_ATTR, request.getRemoteUser());
      return "redirect:/";
    }

    @RequestMapping(value = "readSession")
    public String readSession(HttpServletRequest request) {
      System.out.println("Current username: " + request.getSession().getAttribute(USERNAME_ATTR));
      return "redirect:/";
    }

    @ModelAttribute("username")
    public String username(HttpSession session) {
        return (String) session.getAttribute(USERNAME_ATTR);
    }
}
