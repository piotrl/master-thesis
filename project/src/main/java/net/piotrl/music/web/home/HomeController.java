package net.piotrl.music.web.home;

import net.piotrl.music.api.dashboard.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
class HomeController {

    private final DashboardService dashboardService;

    @Autowired
    public HomeController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
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
        model.addAttribute("scrobblesCount", dashboardService.countScrobbles(1L));
        model.addAttribute("activitiesTimeSum", dashboardService.countActivities( 1l));
        return "home/homeSignedIn";
    }
}