package ballblastx.libraries;

public class Helper {
    public static void sleep (long milisecond) {
        if (milisecond < 0)
            return;

        try {
            Thread.sleep(milisecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static double getEuclideanDistance(float x1, float y1, float x2, float y2) {
        return Math.sqrt( Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) );
    }
}
