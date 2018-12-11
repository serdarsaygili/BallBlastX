package ballblastx.gamepackage;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.Console;
import java.util.Random;

import ballblastx.BallBlastXActivity;

public class Ball {
    float x, y, radius, velocityX, velocityY, startY;
    int count, startCount;
    boolean isGravityStarted = false;
    boolean touchedGround = false;

    public Ball(float radius, int count) {
        this.radius = radius;
        this.count = count;
        this.startCount = count;

        Random random = Settings.getRandom();
        y = random.nextInt(BallBlastXActivity.instance.height / 2);
        x = random.nextBoolean() ? -Settings.maxRadious : BallBlastXActivity.instance.width + Settings.maxRadious;
        startY = y;
        velocityX = x < 0 ? 7 : -7;
        velocityY = 0;
    }

    public void move() {
        x += velocityX;

        if (isGravityStarted) {
            if (x > BallBlastXActivity.instance.width - radius) {
                x = BallBlastXActivity.instance.width - radius;
                velocityX = -Math.abs(velocityX);
            }

            if (x < radius) {
                x = radius;
                velocityX = Math.abs(velocityX);
            }

            if ((velocityY <= Settings.maxVelocity && velocityY >= -2 * Settings.gravity) || !touchedGround || (velocityY < 0 && startY >= y - 0.5 * velocityY * velocityY / Settings.gravity)) {
                velocityY += Settings.gravity;
            }

            y += velocityY;

            if (y > BallBlastXActivity.instance.height - radius) {
                touchedGround = true;
                velocityY = -velocityY;
                velocityY -= Settings.gravity;
            }

        }

        if (x > radius + BallBlastXActivity.instance.width * 0.1 && x < BallBlastXActivity.instance.width * 0.9 - radius) {
            isGravityStarted = true;
        }
    }

    public void onDraw(Canvas canvas, Paint paint) {
        float drawY = y;
        if (drawY > BallBlastXActivity.instance.height - radius) {
            drawY = BallBlastXActivity.instance.height - radius;
        }

        paint.setColor(0xff0000ff);
        canvas.drawCircle(x, drawY, radius, paint);
        paint.setColor(0xffffffff);
        paint.setTextSize(radius);

        float correctY = (paint.descent() + paint.ascent()) / 2;
        canvas.drawText(count + "", x, drawY - correctY, paint);
    }
}
