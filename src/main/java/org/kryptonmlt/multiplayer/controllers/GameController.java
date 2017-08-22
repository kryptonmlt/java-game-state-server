package org.kryptonmlt.multiplayer.controllers;

import java.util.Calendar;
import java.util.List;
import static org.apache.tomcat.jni.User.username;
import org.json.JSONException;
import org.json.JSONObject;
import org.kryptonmlt.multiplayer.enums.ActionEnum;
import org.kryptonmlt.multiplayer.models.db.Game;
import org.kryptonmlt.multiplayer.models.db.Player;
import org.kryptonmlt.multiplayer.models.pojos.GameObject;
import org.kryptonmlt.multiplayer.repositories.GameObjectRepository;
import org.kryptonmlt.multiplayer.repositories.GameRepository;
import org.kryptonmlt.multiplayer.repositories.PlayerRepository;
import org.kryptonmlt.multiplayer.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Kurt
 */
@RestController()
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameObjectRepository gameObjectRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Game> getGames() {
        return (List<Game>) gameRepository.findAll();
    }

    @RequestMapping(value = "/open", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Game> getGamesOpen() {
        return gameRepository.findByDateStartedIsNull();
    }

    /**
     * Retrieves 1 game
     *
     * @param gameId game id
     * @return game pojo from db
     */
    @RequestMapping(value = "/{gameId}", method = RequestMethod.GET, headers = "Accept=application/json")
    public Game getGame(@PathVariable long gameId) {
        return gameRepository.findOne(gameId);
    }

    @RequestMapping(value = "/{gameId}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public void deleteGame(@PathVariable("gameId") long gameId) {
        Game game = gameRepository.findOne(gameId);
        game.setDateStarted(Calendar.getInstance().getTime());
        gameRepository.save(game);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, headers = "Accept=application/json")
    public Long addGame(@RequestParam("secret") String secret, @RequestParam("maxPlayers") String maxPlayers) {
        Game game = gameRepository.save(new Game(secret, Integer.parseInt(maxPlayers)));
        gameObjectRepository.addRoom(game.getId());
        return game.getId();
    }

    @RequestMapping(value = "/{gameId}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public String updateGame(@RequestBody String jsonStr, @PathVariable long gameId) throws JSONException {
        JSONObject jObject = new JSONObject(jsonStr);
        Game game = gameRepository.findOne(gameId);
        if (game.getSecret().equals(jObject.getString("secret"))) {
            if (jObject.has("started")) {
                if (jObject.getBoolean("started")) {
                    game.setDateStarted(Calendar.getInstance().getTime());
                    gameRepository.save(game);
                }
            } else {
                List<Player> players = playerRepository.findByGame(game);
                if (players.size() < game.getMaxPlayers()) {
                    String username = jObject.getString("username");
                    if (Tools.isUsernameDuplicate(username, players)) {
                        return "Username is duplicate";
                    }
                    Player player = playerRepository.save(new Player(username, game));
                    return "" + player.getId();
                }
            }
        }
        return "Invalid Secret";
    }

    @RequestMapping(value = "/{gameId}/started", method = RequestMethod.GET, headers = "Accept=application/json")
    public boolean isGameStarted(@PathVariable long gameId) {
        Game game = gameRepository.findOne(gameId);
        return game.getDateStarted() != null;
    }

    @RequestMapping(value = "/{gameId}/players", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Player> getPlayersInGame(@PathVariable long gameId) {
        Game game = gameRepository.findOne(gameId);
        List<Player> players = playerRepository.findByGame(game);
        return players;
    }
}
