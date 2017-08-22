package org.kryptonmlt.multiplayer;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.kryptonmlt.multiplayer.models.db.Game;
import org.kryptonmlt.multiplayer.repositories.GameObjectRepository;
import org.kryptonmlt.multiplayer.repositories.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kurt
 */
@Component
public class JRGSRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(JRGSRunner.class);

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameObjectRepository gameObjectRepository;

    public static final int HOURS_AGO = 6;

    /**
     * Starts the Game Search Scraper
     *
     * @param strings no arguments used
     * @throws Exception all exceptions should be caught
     */
    @Override
    public void run(String... strings) throws Exception {
        LOGGER.info("Starting JRGS");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -JRGSRunner.HOURS_AGO);
        Date when = calendar.getTime();
        List<Game> games = gameRepository.findAll();
        for (Game game : games) {
            if (game.getDateStarted() == null || game.getDateStarted().after(when)) {
                gameObjectRepository.addRoom(game.getId());
            }
        }

    }

}
