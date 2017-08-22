package org.kryptonmlt.multiplayer.repositories;

import java.util.List;
import org.kryptonmlt.multiplayer.models.db.Game;
import org.kryptonmlt.multiplayer.models.db.Player;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Kurt
 */
public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findByGame(Game game);
}
