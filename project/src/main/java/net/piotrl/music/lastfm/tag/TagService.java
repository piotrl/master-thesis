package net.piotrl.music.lastfm.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

@Service
public class TagService {

    private final TagCrudRepository crudRepository;

    @Autowired
    public TagService(TagCrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public void saveNewTags(List<TagEntity> tags) {
        throw new NotImplementedException();
    }
}
