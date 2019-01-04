package ballblastx.gamepackage;

import android.graphics.Canvas;
import android.graphics.Paint;

import ballblastx.BallBlastXActivity;
import ballblastx.enums.Sound;
import ballblastx.libraries.SoundManager;

public class GameStatus {
    public boolean isFingerPressed;
    public boolean isGameStarted;
    public boolean isLevelEnded;
    public boolean isGameOver;
    public boolean isGameEnded;
    public int level;
    public int score;

    BallManager ballManager;
    BulletManager bulletManager;
    Player player;

    public GameStatus(BallManager ballManager, BulletManager bulletManager, Player player) {
        this.ballManager = ballManager;
        this.bulletManager = bulletManager;
        this.player = player;

        isGameStarted = false;
        isGameEnded = false;
        isGameOver = false;
        isLevelEnded = false;
        isFingerPressed = false;
        level = 1;
        score = 0;

        ballManager.resetLevel(level);
        bulletManager.resetLevel(level);
        player.resetLevel(level);
    }

    private int levelEnder = -1;
    public void gameStep() {
        if (levelEnder > 0) {
            --levelEnder;
        }

        if (levelEnder == 0) {
            isGameStarted = false;
            isLevelEnded = true;
        }

        if (!isGameOver && player.checkIfGameOver()) {
            SoundManager.Play(Sound.GameOver);
            isGameStarted = false;
            isGameOver = true;

            if (score > Settings.bestScore) {
                Settings.bestScore = score;
                BallBlastXActivity.instance.writeSettings();
            }
        }
    }

    public boolean canMove() {
        return isFingerPressed && !isGameOver && !isLevelEnded;
    }

    public boolean isRunning() {
        gameStep();

        return isGameStarted && isFingerPressed && !isLevelEnded && !isGameOver && !isGameEnded;
    }

    public void setFingerStatus(boolean isFingerPressed) {
        this.isFingerPressed = isFingerPressed;

        if (this.isFingerPressed) {
            ballManager.continueLevel();
            bulletManager.continueLevel(); // dont fire again, restart wait duration
            player.continueLevel();
        }
    }

    public void newActivity() {
        if (isLevelEnded) {
            startNextLevel(level + 1);
        }
        else if (isGameOver) {
            startNextLevel(1);
        }
        else if (!isGameStarted) {
            isGameStarted = true;
        }
    }

    public void finishLevel() { // an example of how to delay a result after a specific event
        if (levelEnder == -1) {
            levelEnder = (1000 / Settings.screenRefreshRequestDuration); // delay 50 frames, so total delay duration is 50 x 20 ms = 1 second
            SoundManager.Play(Sound.Completed);
        }
    }

    public void startNextLevel(int newLevel) {
        level = newLevel;

        ballManager.resetLevel(level);
        bulletManager.resetLevel(level);
        player.resetLevel(level);

        isGameOver = false;
        isGameStarted = false;
        isGameEnded = false;
        isLevelEnded = false;
        isFingerPressed = false;
        levelEnder = -1;

        if (level == 1) {
            score = 0;
        }
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

        float textPositionX = w / 2;
        float textPositionY = Settings.playerVerticalPosition / 2;

        if (isGameOver) {
            drawTransparentBackground(canvas, paint);
            canvas.drawText("Game over", textPositionX, textPositionY, paint);
            paint.setColor(0xffffffff);
            paint.setTextSize(Settings.mediumTextSize);
            canvas.drawText("Best Score: " + Settings.bestScore, textPositionX, textPositionY + Settings.largeTextSize, paint);
        }
        else if (isLevelEnded) {
            drawTransparentBackground(canvas, paint);
            canvas.drawText("End of level " + level, textPositionX, textPositionY, paint);
        }
        else if (isGameEnded) {
            drawTransparentBackground(canvas, paint);
            canvas.drawText("Congratulations", textPositionX, textPositionY, paint);
        }
        else if (!isGameStarted) {
            drawTransparentBackground(canvas, paint);
            canvas.drawText("Swipe to start level " + level, textPositionX, textPositionY, paint);
        }
        else if (!isFingerPressed) {
            drawTransparentBackground(canvas, paint);
            canvas.drawText("Paused", textPositionX, textPositionY, paint);
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
