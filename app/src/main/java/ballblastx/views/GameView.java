package ballblastx.views;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import ballblastx.BallBlastXActivity;
import ballblastx.gamepackage.BallManager;
import ballblastx.gamepackage.BulletManager;
import ballblastx.gamepackage.Player;
import ballblastx.libraries.Helper;

public class GameView extends View implements Runnable {

    Paint paint;
    Thread thread;
    Boolean isRunning;
    BulletManager bulletManager;
    BallManager ballManager;
    Player player;
    boolean isFingerPressed;

    public GameView(BallBlastXActivity context) {
        super(context);
        bulletManager = new BulletManager();
        ballManager = new BallManager(bulletManager, 1);
        player = new Player(bulletManager);


        paint = new Paint();
        paint.setColor(Color.RED);
        start();
    }

    @Override
    public void onDraw(Canvas canvas) {
        player.onDraw(canvas, paint);
        bulletManager.onDraw(canvas, paint);
        ballManager.onDraw(canvas, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isFingerPressed = true;
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
            Helper.sleep(20 - computationDuration);
        }
    }

    public void calculateNextStep() {
        if (isFingerPressed) {
            player.fireBullet();
        }
        bulletManager.moveBullets();
        bulletManager.removeBullets();
        ballManager.addBall();
        ballManager.moveBalls();
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
