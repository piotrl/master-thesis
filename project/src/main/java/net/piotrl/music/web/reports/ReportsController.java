package net.piotrl.music.web.reports;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
class ReportsController {

    @GetMapping("/reports")
    String index(Principal principal) {
        return "dashboard/reports";
    }
}