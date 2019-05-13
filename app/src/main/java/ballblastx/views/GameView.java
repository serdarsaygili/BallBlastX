package ballblastx.views;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import ballblastx.BallBlastXActivity;
import ballblastx.gamepackage.BallBlastManager;
import ballblastx.gamepackage.BallManager;
import ballblastx.gamepackage.BulletManager;
import ballblastx.gamepackage.CloudManager;
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
    CloudManager cloudManager;
    BallBlastManager ballBlastManager;
    Player player;
    GameStatus gameStatus;
    Bitmap doubleBufferingImage;
    Canvas fastCanvas;

    public GameView(BallBlastXActivity context) {
        super(context);

        doubleBufferingImage = Bitmap.createBitmap(BallBlastXActivity.instance.width, BallBlastXActivity.instance.height, Bitmap.Config.ARGB_8888);
        fastCanvas = new Canvas(doubleBufferingImage);

        ballBlastManager = new BallBlastManager();
        bulletManager = new BulletManager();
        cloudManager = new CloudManager();
        ballManager = new BallManager(bulletManager, ballBlastManager);
        player = new Player(bulletManager, ballManager);
        gameStatus = new GameStatus(ballManager, bulletManager, player);

        paint = ImageContainer.getPaint();
        start();
    }

    float avgDuration = 0;
    @Override
    public void onDraw(Canvas canvas) {
        long startTime = System.currentTimeMillis();

        int w = BallBlastXActivity.instance.width;
        int h = BallBlastXActivity.instance.height;

        paint.setColor(0xff94EEE9);
        paint.setStyle(Paint.Style.FILL);
        fastCanvas.drawRect(0, 0, w, h, paint);

        paint.setColor(0xffB11848);
        int groundStart = Settings.groundVerticalPositionY;
        fastCanvas.drawRect(0, groundStart, w, h, paint);
        fastCanvas.drawBitmap(LoadingView.ground, 0, groundStart - LoadingView.groundCorrectionHeight, null);

        cloudManager.onDraw(fastCanvas, paint);
        player.onDraw(fastCanvas, paint, gameStatus.isGameOver);
        bulletManager.onDraw(fastCanvas, paint);
        ballBlastManager.onDraw(fastCanvas, paint);
        ballManager.onDraw(fastCanvas, paint);
        gameStatus.onDraw(fastCanvas, paint);

        if (!Settings.isProduction)
        {
            avgDuration = ((System.currentTimeMillis() - startTime) + 5 * avgDuration) / 6;

            paint.setTextAlign(Paint.Align.LEFT);
            paint.setTextSize(w / 25);
            paint.setColor(0xff000000);

            fastCanvas.drawText("Draw Duration: " + (int)avgDuration + " ms (should be < 20 ms)", 20, h - 20, paint);
        }

        canvas.drawBitmap(doubleBufferingImage, 0, 0, null);
    }

    boolean isMoving = false;
    float lastX = BallBlastXActivity.instance.width / 2;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isMoving = true;
                gameStatus.setFingerStatus(true);
                gameStatus.newActivity();
                break;
            case MotionEvent.ACTION_MOVE:
                lastX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                isMoving = true;
                gameStatus.setFingerStatus(false);
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
            if (ballManager.hasCompletedBalls()) {
                gameStatus.finishLevel();
            }
            else {
                player.fireBullet(gameStatus.level + 2);
            }

            if (isMoving)
            {
                if (gameStatus.canMove()) {
                    player.move(lastX);
                }
            }

            ballBlastManager.move();
            cloudManager.move();
            bulletManager.moveBullets();
            bulletManager.removeBullets();
            ballManager.addBall();

            int totalHits = ballManager.moveBalls();
            gameStatus.score += totalHits;
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
