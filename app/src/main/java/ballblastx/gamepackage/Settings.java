package ballblastx.gamepackage;

import java.util.Random;

public class Settings {
    public static boolean isProduction = false; // set false for development mode, set true for production

    public static int screenRefreshRequestDuration = 20;

    public static float bulletSpeedMultiplier = 0.01f;
    public static float playerHorizontalPositionMultiplier = 0.9f;
    public static float maxRadious = 200;
    public static float gravity = 0.6f;
    public static int ballAddingFrequency = 2000;
    public static int ballSizeCaliber = 20;
    public static int maxVelocity = 20;
    public static int debugTextSize = 12;
    public static int playerRadius = 30;
    public static int bulletWidth = 4;
    public static int bulletHeight = 8;

    private static Random random;
    public static Random getRandom() {
        if (random == null) {
            random = new Random(2018);
        }

        return random;
    }

    public static void setConfiguration(int width, int height) { // assume: 812x375
        gravity = height / 1353.3f;
        debugTextSize = height / 67;
        maxRadious = width / 4;
        ballSizeCaliber = width / 30;
        playerRadius = width / 20;
        bulletWidth = (width / 90) / 2 * 2; // should be even value to be symmetric
        bulletHeight = height / 100;
    }
}
