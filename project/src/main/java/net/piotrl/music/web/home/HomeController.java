package net.piotrl.music.web.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
class HomeController {


    @GetMapping("/")
    String index(Model model, Principal principal) {
        if (principal == null) {
            return "home/home";
        }
        model.addAttribute("userName", principal.getName());
        return "home/home";
    }
}