package ballblastx.views;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import ballblastx.BallBlastXActivity;
import ballblastx.gamepackage.BallManager;
import ballblastx.gamepackage.BulletManager;
import ballblastx.gamepackage.FpsCounter;
import ballblastx.gamepackage.GameStatus;
import ballblastx.gamepackage.Player;
import ballblastx.gamepackage.Settings;
import ballblastx.libraries.Helper;
import ballblastx.libraries.ImageContainer;

public class GameView extends View implements Runnable {

    Paint paint;
    Thread thread;
    Boolean isRunning;
    BulletManager bulletManager;
    BallManager ballManager;
    Player player;
    boolean isFingerPressed;
    GameStatus gameStatus;
    Bitmap doubleBufferingImage;
    Canvas fastCanvas;

    public GameView(BallBlastXActivity context) {
        super(context);

        doubleBufferingImage = Bitmap.createBitmap(BallBlastXActivity.instance.width, BallBlastXActivity.instance.height, Bitmap.Config.ARGB_8888);
        fastCanvas = new Canvas(doubleBufferingImage);

        bulletManager = new BulletManager();
        ballManager = new BallManager(bulletManager);
        player = new Player(bulletManager);
        gameStatus = new GameStatus(ballManager, bulletManager);

        paint = ImageContainer.getPaint();
        start();
    }

    @Override
    public void onDraw(Canvas canvas) {
        int w = BallBlastXActivity.instance.width;
        int h = BallBlastXActivity.instance.height;

        paint.setColor(0xffffffff);
        paint.setStyle(Paint.Style.FILL);
        fastCanvas.drawRect(0, 0, w, h, paint);

        paint.setColor(0xff667788);
        int groundStart = Settings.getGroundStart();
        fastCanvas.drawRect(0, groundStart, w, h, paint);

        player.onDraw(fastCanvas, paint);
        bulletManager.onDraw(fastCanvas, paint);
        ballManager.onDraw(fastCanvas, paint);
        gameStatus.onDraw(fastCanvas, paint);

        canvas.drawBitmap(doubleBufferingImage, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isFingerPressed = true;
                gameStatus.newActivity();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isFingerPressed) {
                    player.move(event.getX());
                }
                break;
            case MotionEvent.ACTION_UP:
                isFingerPressed = false;
                break;
        }
        return true;
    }

    @Override
    public void run() {
        while (isRunning) {
            long start = System.currentTimeMillis();

            calculateNextStep();

            this.postInvalidate();

            long end = System.currentTimeMillis();
            long computationDuration = end - start;
            Helper.sleep(Settings.screenRefreshRequestDuration - computationDuration);
        }
    }

    public void calculateNextStep() {
        if (gameStatus.isRunning()) {
            if (isFingerPressed) {
                player.fireBullet(5);
            }
            bulletManager.moveBullets();
            bulletManager.removeBullets();
            ballManager.addBall();

            int totalHits = ballManager.moveBalls();
            gameStatus.score += totalHits;

            if (ballManager.hasCompletedBalls())
                gameStatus.finishLevel();
        }
    }

    public void stop() {
        isRunning = false;
    }

    public void start() {
        isRunning = true;
        if (thread == null) {
            thread = new Thread(this);
        }
        thread.start();
    }
}
