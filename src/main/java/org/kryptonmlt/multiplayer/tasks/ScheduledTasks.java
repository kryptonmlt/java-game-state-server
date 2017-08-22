package org.kryptonmlt.multiplayer.tasks;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.kryptonmlt.multiplayer.JRGSRunner;
import org.kryptonmlt.multiplayer.models.db.Game;
import org.kryptonmlt.multiplayer.repositories.GameObjectRepository;
import org.kryptonmlt.multiplayer.repositories.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
    private GameRepository gameRepository;

    /**
     * A cron job which periodically runs at after 24hrs to retrieve the newest
     * games
     */
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000, initialDelay = 60 * 1000)
    public void deleteOldGames() {
        LOGGER.info("Running cronjob: deleteOldGames");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -JRGSRunner.HOURS_AGO);
        Date when = calendar.getTime();
        List<Game> games = gameRepository.findAll();
        for (Game game : games) {
            if (game.getDateStarted() != null && game.getDateStarted().before(when)) {
                gameObjectRepository.deleteRoom(game.getId());
            }
        }
    }
}
