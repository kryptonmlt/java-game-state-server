package org.kryptonmlt.multiplayer.controllers;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.kryptonmlt.multiplayer.enums.ActionEnum;
import org.kryptonmlt.multiplayer.models.pojos.GameObject;
import org.kryptonmlt.multiplayer.repositories.GameObjectRepository;
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
@RequestMapping("/gameobject")
public class GameObjectController {

    @Autowired
    private GameObjectRepository gameObjectRepository;

    @RequestMapping(value = "/{roomId}", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<GameObject> getGameObject(@PathVariable("roomId") long roomId, @RequestParam("goId") int goId) {
        if (goId == -1) {
            return gameObjectRepository.findAny(roomId);
        }
        List<GameObject> result = new ArrayList<>();
        result.add(gameObjectRepository.findOne(roomId, goId));
        return result;
    }

    @RequestMapping(value = "/{roomId}", method = RequestMethod.POST, consumes = {"application/json"})
    public Integer addGameObject(@PathVariable("roomId") long roomId, @RequestBody String jsonStr) throws JSONException {
        JSONObject jObject = new JSONObject(jsonStr);
        GameObject go = new GameObject(jObject.getString("type"), jObject.getInt("playerId"), 1,
                Tools.convertStringArrayToIntArray(jObject.getString("tile").replace("[", "").replace("]", "").split(",")),
                Tools.convertStringArrayToFloatArray(jObject.getString("position").replace("[", "").replace("]", "").split(",")));
        return gameObjectRepository.save(roomId, go);
    }

    @RequestMapping(value = "/{roomId}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public void updateGameObject(@PathVariable("roomId") long roomId, @RequestParam("goId") int goId,
            @RequestBody String jsonStr) throws JSONException {
        JSONObject jObject = new JSONObject(jsonStr);
        GameObject go = gameObjectRepository.findOne(roomId, goId);
        go.setLastAction(ActionEnum.UPDATE);
        if (jObject.has("position")) {
            go.setPosition(Tools.convertStringArrayToFloatArray(jObject.getString("position").replace("[", "").replace("]", "").split(",")));
        }
        if (jObject.has("quantity")) {
            go.setQuantity(jObject.getInt("quantity"));
        }
        if (jObject.has("tile")) {
            go.setTile(Tools.convertStringArrayToIntArray(jObject.getString("tile").replace("[", "").replace("]", "").split(",")));
        }
    }

    @RequestMapping(value = "/{roomId}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public void deleteGameObject(@PathVariable("roomId") long roomId, @RequestParam("goId") int goId) {
        GameObject go = gameObjectRepository.findOne(roomId, goId);
        go.setLastAction(ActionEnum.DELETE);
    }
}
