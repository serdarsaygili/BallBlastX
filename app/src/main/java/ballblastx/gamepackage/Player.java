package ballblastx.gamepackage;

import android.graphics.Canvas;
import android.graphics.Paint;

import ballblastx.BallBlastXActivity;
import ballblastx.libraries.Helper;

public class Player {
    float x, y;
    BulletManager bulletManager;
    BallManager ballManager;

    public Player(BulletManager bulletManager, BallManager ballManager) {
        x = BallBlastXActivity.instance.width / 2;
        y = Settings.playerVerticalPosition;

        this.bulletManager = bulletManager;
        this.ballManager = ballManager;
    }

    public void continueLevel() {
    }

    public void resetLevel(int level) {
        x = BallBlastXActivity.instance.width / 2;
    }

    public boolean checkIfGameOver() {
        synchronized (ballManager.balls) {
            for (Ball ball : ballManager.balls) {
                double distance = Helper.getEuclideanDistance(x, y, ball.x, ball.y);

                if (distance < Settings.playerRadius + ball.radius) {
                    return true;
                }
            }
        }

        return false;
    }

    public void move(float fingerX) {
        x = (2 * x + fingerX) / 3;
    }

    public void onDraw(Canvas canvas, Paint paint, boolean isGameOver) {
        if (isGameOver) {
            paint.setColor(0xffff0000);
        }
        else {
            paint.setColor(0xff00ff00);
        }

        canvas.drawCircle(x, y, Settings.playerRadius, paint);
    }

    public void fireBullet(int numBullets) {
        bulletManager.addBullet(x,y - 5, numBullets);
    }
}
