package org.kryptonmlt.multiplayer.models.pojos;

import org.kryptonmlt.multiplayer.enums.ActionEnum;

/**
 *
 * @author Kurt
 */
public class GameObject {

    private int id;
    private final String type;
    private final int playerId;
    private int quantity;
    private int[] tile = new int[2]; //x,y
    private float[] position = new float[3]; //x,y,z
    private ActionEnum lastAction;

    public GameObject(String type, int playerId, int quantity, int[] tile, float[] position) {
        this.type = type;
        this.playerId = playerId;
        this.quantity = quantity;
        this.tile = tile;
        this.position = position;
        lastAction = ActionEnum.CREATE;
    }

    public ActionEnum getLastAction() {
        return lastAction;
    }

    public void setLastAction(ActionEnum lastAction) {
        if (this.lastAction != ActionEnum.DELETE) {
            this.lastAction = lastAction;
        } else {
            System.err.println("Trying to " + lastAction + " but Object " + id + " is already DELETED");
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int[] getTile() {
        return tile;
    }

    public void setTile(int[] tile) {
        this.tile = tile;
    }

    public float[] getPosition() {
        return position;
    }

    public void setPosition(float[] position) {
        this.position = position;
    }

    public int getPlayerId() {
        return playerId;
    }

    @Override
    public String toString() {
        return "GameObject{" + "id=" + id + ", type=" + type + ", playerId=" + playerId + ", quantity=" + quantity + ", tile=" + tile + ", position=" + position + '}';
    }

}
