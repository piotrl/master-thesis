package net.piotrl.music.api.raw;

import net.piotrl.music.core.config.ApiUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
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
                                                        ApiUser apiUser) {
        Assert.notNull(apiUser);
        return rawDataRepository.monthlyActivities(year, month, apiUser.getId());
    }

    @RequestMapping("activities/from/{from}/to/{to}")
    public List<RawActivity> rawActivities(@PathVariable String from,
                                           @PathVariable String to,
                                           ApiUser apiUser) {
        Assert.notNull(apiUser);
        return rawDataRepository.rawActivities(from, to, apiUser.getId());
    }

    @RequestMapping("scrobbles/from/{from}/to/{to}")
    public List<RawScrobbles> rawScrobbles(@PathVariable String from,
                                           @PathVariable String to,
                                           ApiUser apiUser) {
        Assert.notNull(apiUser);
        return rawDataRepository.rawScrobbles(from, to, apiUser.getId());
    }


}
