package org.kryptonmlt.multiplayer.controllers;

import java.util.Calendar;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.kryptonmlt.multiplayer.models.db.Player;
import org.kryptonmlt.multiplayer.models.db.Room;
import org.kryptonmlt.multiplayer.repositories.GameObjectRepository;
import org.kryptonmlt.multiplayer.repositories.PlayerRepository;
import org.kryptonmlt.multiplayer.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.kryptonmlt.multiplayer.repositories.RoomRepository;

/**
 *
 * @author Kurt
 */
@RestController()
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameObjectRepository gameObjectRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Room> getAll() {
        return (List<Room>) roomRepository.findAll();
    }

    @RequestMapping(value = "/open", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Room> getOpen() {
        return roomRepository.findByDateStartedIsNull();
    }

    /**
     * Retrieves 1 game
     *
     * @param roomId game id
     * @return game pojo from db
     */
    @RequestMapping(value = "/{roomId}", method = RequestMethod.GET, headers = "Accept=application/json")
    public Room get(@PathVariable long roomId) {
        return roomRepository.findOne(roomId);
    }

    @RequestMapping(value = "/{roomId}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public void delete(@PathVariable("roomId") long roomId) {
        Room room = roomRepository.findOne(roomId);
        room.setDateStarted(Calendar.getInstance().getTime());
        roomRepository.save(room);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, headers = "Accept=application/json")
    public Long add(@RequestParam("secret") String secret, @RequestParam("maxPlayers") String maxPlayers) {
        Room room = roomRepository.save(new Room(secret, Integer.parseInt(maxPlayers)));
        gameObjectRepository.addRoom(room.getId());
        return room.getId();
    }

    @RequestMapping(value = "/{roomId}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public String update(@RequestBody String jsonStr, @PathVariable long roomId) throws JSONException {
        JSONObject jObject = new JSONObject(jsonStr);
        Room room = roomRepository.findOne(roomId);
        if (room.getSecret().equals(jObject.getString("secret"))) {
            if (jObject.has("started")) {
                if (jObject.getBoolean("started")) {
                    room.setDateStarted(Calendar.getInstance().getTime());
                    roomRepository.save(room);
                }
            } else {
                List<Player> players = playerRepository.findByRoom(room);
                if (players.size() < room.getMaxPlayers()) {
                    String username = jObject.getString("username");
                    if (Tools.isUsernameDuplicate(username, players)) {
                        return "Username is duplicate";
                    }
                    Player player = playerRepository.save(new Player(username, room));
                    return "" + player.getId();
                }
            }
        }
        return "Invalid Secret";
    }

    @RequestMapping(value = "/{roomId}/started", method = RequestMethod.GET, headers = "Accept=application/json")
    public boolean isStarted(@PathVariable long roomId) {
        Room room = roomRepository.findOne(roomId);
        return room.getDateStarted() != null;
    }

    @RequestMapping(value = "/{roomId}/players", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Player> getPlayersInRoom(@PathVariable long roomId) {
        Room room = roomRepository.findOne(roomId);
        List<Player> players = playerRepository.findByRoom(room);
        return players;
    }
}
