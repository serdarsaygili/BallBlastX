package ballblastx.gamepackage;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.Console;
import java.util.Random;

import ballblastx.BallBlastXActivity;
import ballblastx.views.LoadingView;

public class Ball {
    float x, y, radius, velocityX, velocityY, startY;
    public int ballSizeIndex;
    int count, startCount;
    boolean isGravityStarted = false;
    boolean touchedGround = false;

    public Ball(float diamater, int ballSizeIndex, int count) {
        this.ballSizeIndex = ballSizeIndex;
        this.radius = diamater / 2;
        this.count = count;
        this.startCount = count;

        Random random = Settings.getRandom();
        y = random.nextInt(BallBlastXActivity.instance.height / 2);
        x = random.nextBoolean() ? -Settings.maxRadious : BallBlastXActivity.instance.width + Settings.maxRadious;
        startY = y;
        velocityX = -Math.signum(x) * Settings.velocityX;
        velocityY = 0;
    }

    public void move() {
        int groundStart = Settings.getGroundStart();

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

            if (y > groundStart - radius) {
                touchedGround = true;
                velocityY = Settings.maxVelocityYs.get(ballSizeIndex);
                velocityY = -10;
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

        int ballIndex = 0;

        for (int i = 9; i >= 0; i--) {
            if (count < i * 10) {
                ballIndex = i;
            }
        }

        ballIndex += ballSizeIndex * 10;
        float currentTopY = drawY - radius;
        if (currentTopY + 2 * radius > Settings.getGroundStart()) {
            currentTopY = Settings.getGroundStart() - 2 * radius;
        }

        canvas.drawBitmap(LoadingView.Balls.get(ballIndex), x - radius, currentTopY, null);

        paint.setColor(0xffffffff);
        paint.setTextSize(radius);

        float correctY = (paint.descent() + paint.ascent()) / 2;
        canvas.drawText(count + "", x, currentTopY + radius - correctY, paint);
    }
}
