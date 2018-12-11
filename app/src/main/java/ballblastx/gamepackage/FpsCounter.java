package ballblastx.gamepackage;

import android.graphics.Canvas;
import android.graphics.Paint;

public class FpsCounter {
    int runCount;
    int viewCount;

    public FpsCounter() {
        runCount = 1;
        viewCount = 0;
    }

    public void addRun() {
        ++runCount;
    }

    private void addView() {
        ++viewCount;
    }

    private void updateStatus() {
        if (runCount > 1000) {
            runCount *= 0.9;
            viewCount *= 0.9;
        }
    }

    public float getFps()
    {
        addView();
        updateStatus();

        float fps = (1000.0f * viewCount) / (Settings.screenRefreshRequestDuration * runCount);

        return (int)(100 * fps) / 100.0f;
    }

    public void onDraw(Canvas canvas, Paint paint) {
        if (Settings.isProduction)
            return;

        int textSize = Settings.debugTextSize;
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(0xff000000);

        canvas.drawText("FPS: " + getFps(), textSize, 2 * textSize, paint);
    }
}
