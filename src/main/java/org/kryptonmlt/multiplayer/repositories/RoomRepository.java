package org.kryptonmlt.multiplayer.repositories;

import java.util.List;
import org.kryptonmlt.multiplayer.models.db.Room;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Kurt
 */
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByDateStartedIsNull();

}
