package org.kryptonmlt.multiplayer.tasks;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.kryptonmlt.multiplayer.JRGSRunner;
import org.kryptonmlt.multiplayer.models.db.Room;
import org.kryptonmlt.multiplayer.repositories.GameObjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.kryptonmlt.multiplayer.repositories.RoomRepository;

/**
 * Class which stores periodic jobs, namely updating the games list each day
 *
 * @author Kurt
 */
@Component
public class ScheduledTasks {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private GameObjectRepository gameObjectRepository;

    @Autowired
    private RoomRepository roomRepository;

    /**
     * A cron job which periodically runs at after 24hrs to retrieve the newest
     * games
     */
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000, initialDelay = 60 * 1000)
    public void deleteOldRooms() {
        LOGGER.info("Running cronjob: deleteOldRooms");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -JRGSRunner.HOURS_AGO);
        Date when = calendar.getTime();
        List<Room> rooms = roomRepository.findAll();
        for (Room room : rooms) {
            if (room.getDateStarted() != null && room.getDateStarted().before(when)) {
                gameObjectRepository.deleteRoom(room.getId());
            }
        }
    }
}
