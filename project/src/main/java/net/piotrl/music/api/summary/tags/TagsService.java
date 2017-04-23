package net.piotrl.music.api.summary.tags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TagsService {

    private final TagsRepository tagsRepository;

    @Autowired
    public TagsService(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    public List<TagSummary> mostPopularTags(int year, int month, long accountId) {
        LocalDate monthDate = LocalDate.of(year, month, 1);
        return tagsRepository.mostPopularTagsInMonth(monthDate, accountId);
    }
}
