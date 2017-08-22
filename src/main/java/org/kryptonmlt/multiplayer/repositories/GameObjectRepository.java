package org.kryptonmlt.multiplayer.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.kryptonmlt.multiplayer.generators.IDGenerator;
import org.kryptonmlt.multiplayer.models.pojos.GameObject;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kurt
 */
@Component
public class GameObjectRepository {

    private final Map<Long, Map<Integer, GameObject>> roomGameObjectMap = new HashMap<>();
    private final Map<Long, IDGenerator> roomIdGenerator = new HashMap<>();

    public GameObject findOne(Long gameId, Integer gameObjectId) {
        return roomGameObjectMap.get(gameId).get(gameObjectId);
    }

    public Integer save(Long gameId, GameObject gameObject) {
        gameObject.setId(roomIdGenerator.get(gameId).getNextId());
        roomGameObjectMap.get(gameId).put(gameObject.getId(), gameObject);
        return gameObject.getId();
    }

    public void addRoom(Long gameId) {
        roomGameObjectMap.put(gameId, new HashMap<Integer, GameObject>());
        roomIdGenerator.put(gameId, new IDGenerator());
    }

    public List<GameObject> findAny(long gameId) {
        return new ArrayList<>(roomGameObjectMap.get(gameId).values());
    }

    public void deleteRoom(Long gameId) {
        roomGameObjectMap.remove(gameId);
        roomIdGenerator.remove(gameId);
    }
}
