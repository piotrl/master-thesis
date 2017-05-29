package net.piotrl.music.web.dashboard;

import net.piotrl.music.web.home.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
class DashboardController {

    private final HomeService homeService;

    @Autowired
    DashboardController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/summary")
    String summary(Model model, Principal principal) {
        model.addAttribute("summary", homeService.summary(1L));

        return "home/homeSignedIn";
    }

    @GetMapping("/music")
    String music(Principal principal) {
        return "dashboard/reports";
    }

    @GetMapping("/productivity")
    String productivity(Principal principal) {
        return "dashboard/productivity";
    }
}