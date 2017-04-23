package net.piotrl.music.web.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
class DashboardController {

    @GetMapping("/reports")
    String index(Principal principal) {
        return "dashboard/reports";
    }

    @GetMapping("/productivity")
    String productivityls(Principal principal) {
        return "dashboard/productivity";
    }
}