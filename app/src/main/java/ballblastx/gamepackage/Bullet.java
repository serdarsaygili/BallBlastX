package ballblastx.gamepackage;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

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
        int widthDiff = Settings.bulletWidth / 2;
        int heightDiff = Settings.bulletHeight / 2;

        Path path = new Path();
        path.moveTo(x, y - heightDiff);
        path.lineTo(x + widthDiff, y - heightDiff + widthDiff);
        path.lineTo(x + widthDiff, y + heightDiff);
        path.lineTo(x - widthDiff, y + heightDiff);
        path.lineTo(x - widthDiff, y - heightDiff + widthDiff);
        path.close();

        canvas.drawPath(path, paint);
    }
}
