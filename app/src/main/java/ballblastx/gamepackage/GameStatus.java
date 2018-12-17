package ballblastx.gamepackage;

import android.graphics.Canvas;
import android.graphics.Paint;

import ballblastx.BallBlastXActivity;

public class GameStatus {
    public boolean isGameStarted;
    public boolean isLevelEnded;
    public boolean isGameOver;
    public boolean isGameEnded;
    public int level;
    public int score;

    BallManager ballManager;
    BulletManager bulletManager;

    public GameStatus(BallManager ballManager, BulletManager bulletManager) {
        this.ballManager = ballManager;
        this.bulletManager = bulletManager;

        isGameStarted = false;
        isGameEnded = false;
        isGameOver = false;
        isLevelEnded = false;
        level = 1;
        score = 0;

        ballManager.resetLevel(level);
        bulletManager.resetLevel(level);
    }

    public boolean isRunning()
    {
        return isGameStarted && !isLevelEnded && !isGameOver && !isGameEnded;
    }

    public void newActivity() {
        if (!isGameStarted) {
            isGameStarted = true;
        }
        else if (isLevelEnded) {
            startNextLevel();
        }
    }

    public void finishLevel() {
        isLevelEnded = true;
    }

    public void startNextLevel() {
        isGameStarted = false;
        isLevelEnded = false;
        ++level;

        ballManager.resetLevel(level);
        bulletManager.resetLevel(level);
    }

    public void onDraw(Canvas canvas, Paint paint) {
        int w = BallBlastXActivity.instance.width;
        int h = BallBlastXActivity.instance.height;

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(Settings.extraLargeTextSize);

        paint.setColor(0xffaaaa88);
        canvas.drawText(score + "", w / 2, h / 10, paint);

        paint.setColor(0xffffff00);
        canvas.drawText(score + "", w / 2 - 2, h / 10 - 2, paint);

        paint.setTextSize(Settings.largeTextSize);

        if (!isGameStarted) {
            drawTransparentBackground(canvas, paint);
            canvas.drawText("Swipe to start level " + level, w / 2, h / 2, paint);
        }

        if (isGameOver) {
            drawTransparentBackground(canvas, paint);
            canvas.drawText("Game over", w / 2, h / 2, paint);
        }

        if (isLevelEnded) {
            drawTransparentBackground(canvas, paint);
            canvas.drawText("End of level " + level, w / 2, h / 2, paint);
        }

        if (isGameEnded) {
            drawTransparentBackground(canvas, paint);
            canvas.drawText("Congratulations", w / 2, h / 2, paint);
        }
    }

    private void drawTransparentBackground(Canvas canvas, Paint paint)
    {
        int w = BallBlastXActivity.instance.width;
        int h = BallBlastXActivity.instance.height;

        paint.setColor(0x88000000);
        canvas.drawRect(0, 0, w, h, paint);
        paint.setColor(0xffffffff);
    }
}
