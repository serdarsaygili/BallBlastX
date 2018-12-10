package ballblastx.gamepackage;

import android.graphics.Canvas;
import android.graphics.Paint;

import ballblastx.BallBlastXActivity;

public class Bullet {
    public float x, y;

    public Bullet(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        float screenHeight = BallBlastXActivity.instance.height;
        float bulletSpeed = screenHeight * Settings.bulletSpeedMultiplier;
        y -= bulletSpeed;
    }

    public void onDraw(Canvas canvas, Paint paint) {
        canvas.drawRect(x-2, y-2, x+2, y+2, paint);
    }
}
