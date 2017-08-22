package org.kryptonmlt.multiplayer.repositories;

import java.util.List;
import org.kryptonmlt.multiplayer.models.db.Game;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Kurt
 */
public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findByDateStartedIsNull();

}
