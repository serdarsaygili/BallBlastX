package ballblastx.gamepackage;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;

import ballblastx.BallBlastXActivity;
import ballblastx.libraries.ImageContainer;

public class Bullet {
    public float x, y;

    public Bullet(float x, float y) {
        this.x = (int)x;
        this.y = (int)y;
    }

    public void move() {
        float screenHeight = BallBlastXActivity.instance.height;
        int bulletSpeed = (int)(screenHeight * Settings.bulletSpeedMultiplier);
        y -= bulletSpeed;
    }

    public void onDraw(Canvas canvas, Paint paint) {

        int widthDiff = Settings.bulletWidth / 2;
        int heightDiff = Settings.bulletHeight / 2;

        canvas.drawBitmap(ImageContainer.bullet, x - widthDiff, y - heightDiff,null);


/*
        Path path = new Path();
        path.moveTo(x, y - heightDiff);
        path.lineTo(x + widthDiff, y - heightDiff + widthDiff);
        path.lineTo(x + widthDiff, y + heightDiff);
        path.lineTo(x - widthDiff - 1, y + heightDiff);
        path.lineTo(x - widthDiff - 1, y - heightDiff + widthDiff);
        path.close();

        canvas.drawPath(path, paint);*/
    }
}
