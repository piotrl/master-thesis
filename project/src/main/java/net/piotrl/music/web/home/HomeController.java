package net.piotrl.music.web.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
class HomeController {

    private final HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @ModelAttribute("module")
    String module() {
        return "home";
    }

    @GetMapping("/")
    String index(Model model, Principal principal) {
        if (principal == null) {
            return "home/homeNotSignedIn";
        }
        model.addAttribute("userName", principal.getName());
        model.addAttribute("summary", homeService.summary(1L));
        return "home/homeSignedIn";
    }
}