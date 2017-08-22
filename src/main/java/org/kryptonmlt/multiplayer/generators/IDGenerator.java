package org.kryptonmlt.multiplayer.generators;

/**
 *
 * @author Kurt
 */
public class IDGenerator {

    private int id;

    public IDGenerator() {

    }

    public int getNextId() {
        int tempId = id;
        id++;
        return tempId;
    }
}
