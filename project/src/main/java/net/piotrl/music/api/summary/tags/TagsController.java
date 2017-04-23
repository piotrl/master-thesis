package net.piotrl.music.api.summary.tags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/tags")
public class TagsController {

    private final TagsService tagsService;

    @Autowired
    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @RequestMapping("year/{year}/month/{month}/popular")
    public List<TagSummary> mostPopularTags(@PathVariable int year,
                                            @PathVariable int month,
                                            Principal principal) {
//        Assert.notNull(principal);
        return tagsService.mostPopularTags(year, month, 1l);
    }
}
