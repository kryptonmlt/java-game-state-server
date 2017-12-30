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

    public GameObject findOne(Long roomId, Integer gameObjectId) {
        return roomGameObjectMap.get(roomId).get(gameObjectId);
    }

    public Integer save(Long roomId, GameObject gameObject) {
        gameObject.setId(roomIdGenerator.get(roomId).getNextId());
        roomGameObjectMap.get(roomId).put(gameObject.getId(), gameObject);
        return gameObject.getId();
    }

    public void addRoom(Long roomId) {
        roomGameObjectMap.put(roomId, new HashMap<Integer, GameObject>());
        roomIdGenerator.put(roomId, new IDGenerator());
    }

    public List<GameObject> findAny(long roomId) {
        return new ArrayList<>(roomGameObjectMap.get(roomId).values());
    }

    public void deleteRoom(Long roomId) {
        roomGameObjectMap.remove(roomId);
        roomIdGenerator.remove(roomId);
    }
}
