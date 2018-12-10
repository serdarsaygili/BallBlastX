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
}
