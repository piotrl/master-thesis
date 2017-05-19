package net.piotrl.music.modules.lastfm.track.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ScrobbleCrudRepository extends CrudRepository<ScrobbleEntity, Integer> {

    List<ScrobbleEntity> findAllByAccountId(long accountId);
    int countByAccountId(long accountId);


}
