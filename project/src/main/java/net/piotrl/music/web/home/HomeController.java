package net.piotrl.music.web.home;

import net.piotrl.music.core.config.ApiUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
class HomeController {


    @GetMapping("/")
    String index(Model model, ApiUser apiUser) {
        if (apiUser == null) {
            return "home/home";
        }
        model.addAttribute("userName", apiUser.getUsername());
        return "home/home";
    }
}