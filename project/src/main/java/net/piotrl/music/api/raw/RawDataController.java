package net.piotrl.music.api.raw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/raw")
public class RawDataController {

    @Autowired
    private RawDataRepository rawDataRepository;

    @RequestMapping("activities/year/{year}/month/{month}")
    public List<Integer> monthlyActivities(@PathVariable int year,
                                           @PathVariable int month,
                                           Principal principal) {
        Assert.notNull(principal);
        return rawDataRepository.monthlyActivities(year, month, 1l);
    }

    @RequestMapping("activities/from/{from}/to/{to}")
    public void rawActivities(@PathVariable String from,
                              @PathVariable String to,
                              Principal principal) {
        Assert.notNull(principal);
        rawDataRepository.rawActivities(from, to, 1l);
    }

    @RequestMapping("scrobbles/from/{from}/to/{to}")
    public void rawScrobbles(@PathVariable String from,
                             @PathVariable String to,
                             Principal principal) {
        Assert.notNull(principal);
        rawDataRepository.rawScrobbles(from, to, 1l);
    }


}
