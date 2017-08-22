package org.kryptonmlt.multiplayer.utils;

import java.util.List;
import org.kryptonmlt.multiplayer.models.db.Player;

/**
 *
 * @author Kurt
 */
public class Tools {

    private Tools() {

    }

    public static boolean isUsernameDuplicate(String username, List<Player> players) {
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static int[] convertStringArrayToIntArray(String[] input) {
        int[] output = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            output[i] = Integer.parseInt(input[i]);
        }
        return output;
    }

    public static float[] convertStringArrayToFloatArray(String[] input) {
        float[] output = new float[input.length];
        for (int i = 0; i < input.length; i++) {
            output[i] = Float.parseFloat(input[i]);
        }
        return output;
    }
}
