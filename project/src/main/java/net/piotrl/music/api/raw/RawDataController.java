package net.piotrl.music.api.raw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/raw")
public class RawDataController {

    private final RawDataRepository rawDataRepository;

    @Autowired
    public RawDataController(RawDataRepository rawDataRepository) {
        this.rawDataRepository = rawDataRepository;
    }

    @RequestMapping("activities/year/{year}/month/{month}")
    public Map<Integer, List<Double>> monthlyActivities(@PathVariable int year,
                                                      @PathVariable int month,
                                                      Principal principal) {
//        Assert.notNull(principal);
        return rawDataRepository.monthlyActivities(year, month, 1l);
    }

    @RequestMapping("activities/from/{from}/to/{to}")
    public List<RawActivity> rawActivities(@PathVariable String from,
                                           @PathVariable String to,
                                           Principal principal) {
//        Assert.notNull(principal);
        return rawDataRepository.rawActivities(from, to, 1l);
    }

    @RequestMapping("scrobbles/from/{from}/to/{to}")
    public List<RawScrobbles> rawScrobbles(@PathVariable String from,
                                           @PathVariable String to,
                                           Principal principal) {
//        Assert.notNull(principal);
        return rawDataRepository.rawScrobbles(from, to, 1l);
    }


}
