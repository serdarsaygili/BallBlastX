package ballblastx.gamepackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Settings {
    public static boolean isProduction = false; // set false for development mode, set true for production

    public static int screenRefreshRequestDuration = 20;

    public static float bulletSpeedMultiplier = 0.01f;
    public static float maxRadious = 200;
    public static float gravity = 0.5f; // balls come very fast, give me some time to think, changed from 0.6 to 0.5
    public static int ballAddingFrequency = 2000;
    public static int ballSizeCaliber = 20;
    public static int maxVelocity = 1000; // I dont like this parameter, so i am changing it from 20 to 1000
    public static int debugTextSize = 12;
    public static int mediumTextSize = 24;
    public static int largeTextSize = 36;
    public static int extraLargeTextSize = 64;
    public static int bulletWidth = 5;
    public static int bulletHeight = 8;
    public static int bestScore = 0;
    public static int bodyWidth = 80;
    public static int bodyHeight = 60;
    public static int bodyWheelRadius = 20;
    public static int bodyGroundDistance = 5;

    public static int groundVerticalPositionY = 600;
    public static int velocityX = 5;
    public static List <Integer> maxVelocityYs;

    private static Random random;
    public static Random getRandom() {
        if (random == null) {
            random = new Random(2018);
        }

        return random;
    }

    public static void setConfiguration(int width, int height) { // assume: 812x375
        gravity = height / 1600f;
        debugTextSize = height / 67;
        mediumTextSize = height / 33;
        largeTextSize = height / 22;
        extraLargeTextSize = height / 13;
        maxRadious = width / 4;
        ballSizeCaliber = width / 30;
        bulletWidth = (width / 90) / 2 * 2 + 1; // should be odd value to be symmetric
        bulletHeight = height / 100;
        groundVerticalPositionY = (int)(height * 0.7f) + width / 20;
        velocityX = width / 100;
        bodyWidth = width / 8; // 100
        bodyHeight = (int)(bodyWidth * 110 / 126.0);
        bodyWheelRadius = bodyWidth / 4;
        bodyGroundDistance = 7 * bodyWheelRadius / 4;

        maxVelocityYs = new ArrayList<Integer>();
        maxVelocityYs.add((int)(height * 1.1 / gravity));
        maxVelocityYs.add((int)(height * 0.8 / gravity));
        maxVelocityYs.add((int)(height * 0.6 / gravity));
        maxVelocityYs.add((int)(height * 0.35 / gravity));
    }
}
