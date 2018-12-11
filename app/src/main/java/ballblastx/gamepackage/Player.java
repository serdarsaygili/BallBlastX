package ballblastx.gamepackage;

import android.graphics.Canvas;
import android.graphics.Paint;

import ballblastx.BallBlastXActivity;

public class Player {
    float x, y;
    BulletManager bulletManager;

    public Player(BulletManager bulletManager) {
        x = BallBlastXActivity.instance.width / 2;
        y = BallBlastXActivity.instance.height * Settings.playerHorizontalPositionMultiplier;
        this.bulletManager = bulletManager;
    }

    public void move(float fingerX) {
        x = (2 * x + fingerX) / 3;
    }

    public void onDraw(Canvas canvas, Paint paint) {
        paint.setColor(0xff00ff00);
        canvas.drawCircle(x, y, Settings.playerRadius, paint);
    }

    public void fireBullet() {
        bulletManager.addBullet(x,y - 5);
    }
}
