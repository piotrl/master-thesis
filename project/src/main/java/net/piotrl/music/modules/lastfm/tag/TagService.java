package net.piotrl.music.modules.lastfm.tag;

import net.piotrl.music.modules.lastfm.tag.repository.TagCrudRepository;
import net.piotrl.music.modules.lastfm.tag.repository.TagEntity;
import net.piotrl.music.modules.lastfm.tag.repository.TagTrackCrudRepository;
import net.piotrl.music.modules.lastfm.tag.repository.TagTrackEntity;
import net.piotrl.music.modules.lastfm.track.repository.TrackEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class TagService {

    private final TagCrudRepository crudRepository;
    private final TagTrackCrudRepository tagTrackCrudRepository;

    @Autowired
    public TagService(TagCrudRepository crudRepository,
                      TagTrackCrudRepository tagTrackCrudRepository) {
        this.crudRepository = crudRepository;
        this.tagTrackCrudRepository = tagTrackCrudRepository;
    }

    public void saveTagTrackRelation(TrackEntity track, List<TagEntity> tagEntities) {
        List<TagTrackEntity> tagTrackRelations = tagEntities.stream()
                .map(tag -> new TagTrackEntity(tag.getId(), track.getId()))
                .collect(toList());
        tagTrackCrudRepository.save(tagTrackRelations);
    }

    public List<TagEntity> saveTags(Collection<String> tags) {
        return tags.stream()
                .map(this::insertIfExists)
                .collect(toList());
    }

    private TagEntity insertIfExists(String tag) {
        Optional<TagEntity> existingTag = crudRepository.findFirstByName(tag);
        if (existingTag.isPresent()) {
            return existingTag.get();
        }

        TagEntity tagEntity = new TagEntity(tag);
        return crudRepository.save(tagEntity);
    }
}
