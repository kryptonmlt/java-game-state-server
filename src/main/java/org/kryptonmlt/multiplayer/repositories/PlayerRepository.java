package org.kryptonmlt.multiplayer.repositories;

import java.util.List;
import org.kryptonmlt.multiplayer.models.db.Player;
import org.kryptonmlt.multiplayer.models.db.Room;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Kurt
 */
public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findByRoom(Room room);
}
