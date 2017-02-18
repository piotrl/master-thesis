package net.piotrl.music.api.raw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("raw")
public class RawDataController {

    @Autowired
    private RawDataRepository rawDataRepository;

    @GetMapping("activities/from/{from}/to/{to}")
    @ResponseStatus(value = HttpStatus.OK)
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public void rawActivities(@PathVariable LocalDateTime from,
                              @PathVariable LocalDateTime to,
                              Principal principal) {
        Assert.notNull(principal);
        rawDataRepository.rawActivities(from, to, 1l);
    }

    @GetMapping("scrobbles/from/{from}/to/{to}")
    @ResponseStatus(value = HttpStatus.OK)
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public void rawScrobbles(@PathVariable LocalDateTime from,
                             @PathVariable LocalDateTime to,
                             Principal principal) {
        Assert.notNull(principal);
        rawDataRepository.rawScrobbles(from, to, 1l);
    }


}
