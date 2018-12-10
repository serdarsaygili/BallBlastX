package ballblastx.gamepackage;

import java.util.Random;

public class Settings {
    public static float bulletSpeedMultiplier = 0.01f;
    public static float playerHeightMultipllier = 0.9f;
    public static float maxRadious = 200;
    private static Random random;
    public static float gravity = 0.6f;
    public static int ballAddingFrequency = 2000;
    public static int ballSizeCaliber = 20;
    public static int maxVelocity = 20;

    public static Random getRandom() {
        if (random == null) {
            random = new Random(2018);
        }

        return random;
    }
}
