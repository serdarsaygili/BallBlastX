package ballblastx.gamepackage;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.List;

import ballblastx.BallBlastXActivity;
import ballblastx.libraries.Helper;
import ballblastx.views.LoadingView;

public class Player {
    float x, y;
    BulletManager bulletManager;
    BallManager ballManager;

    public Player(BulletManager bulletManager, BallManager ballManager) {
        x = BallBlastXActivity.instance.width / 2; // center
        y = Settings.groundVerticalPositionY - Settings.bodyHeight - 2 * Settings.bodyWheelRadius + Settings.bodyGroundDistance;

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

            List<CircularCollision> collisions = LoadingView.Collisions.get(0);

            for (Ball ball : ballManager.balls) {
                for (CircularCollision circle : collisions)
                {
                    float circleX = x - Settings.bodyWidth / 2 + circle.X * Settings.bodyWidth / 1000;
                    float circleY = y + circle.Y * Settings.bodyHeight / 1000;
                    float circleR = circle.R * Settings.bodyWidth / 1000;

                    double distance = Helper.getEuclideanDistance(circleX, circleY, ball.x, ball.y);

                    if (distance < circleR + ball.radius) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void move(float fingerX) {
        x = (5 * x + fingerX) / 6;
    }

    public void onDraw(Canvas canvas, Paint paint, boolean isGameOver) {
        canvas.drawBitmap(LoadingView.body, x - Settings.bodyWidth / 2, y, null );

        paint.setStyle(Paint.Style.STROKE);
        // wheels
        paint.setColor(0xff000000);
        paint.setStrokeWidth(Settings.bodyWheelRadius / 3);
        int wheelY = Settings.groundVerticalPositionY - Settings.bodyWheelRadius;
        canvas.drawCircle(x - Settings.bodyWidth / 2, wheelY, Settings.bodyWheelRadius, paint);
        canvas.drawCircle(x + Settings.bodyWidth / 2, wheelY, Settings.bodyWheelRadius, paint);

        // wheel arms
        DrawWheelArms(canvas, paint, x - Settings.bodyWidth / 2, wheelY, Settings.bodyWheelRadius);
        DrawWheelArms(canvas, paint, x + Settings.bodyWidth / 2, wheelY, Settings.bodyWheelRadius);

        if (!Settings.isProduction && Settings.debugCollisions)
        {
            paint.setStrokeWidth(1);
            paint.setColor(isGameOver ? 0xFFFF0000 : 0xFFFFFF00);

            List<CircularCollision> collisions = LoadingView.Collisions.get(0);
            for (CircularCollision circle : collisions)
            {
                float circleX = x - Settings.bodyWidth / 2 + circle.X * Settings.bodyWidth / 1000;
                float circleY = y + circle.Y * Settings.bodyHeight / 1000;
                float circleR = circle.R * Settings.bodyWidth / 1000;

                canvas.drawCircle(circleX, circleY, circleR, paint);
            }
        }

        paint.setStyle(Paint.Style.FILL);
    }

    private void DrawWheelArms(Canvas canvas, Paint paint, double x, double y, double r)
    {
        paint.setStrokeWidth(Settings.bodyWheelRadius / 5);

        int numWheelArms = 3;
        double wheelAngle = 2 * Math.PI / numWheelArms;
        double diffCenter = x - BallBlastXActivity.instance.width / 2;
        double radian = 2 * diffCenter / (Settings.bodyWheelRadius) / Math.PI;

        for (int i = 0; i < numWheelArms; ++i)
        {
            double angle = wheelAngle * i + radian;

            double startX = x + Math.cos(angle) * r;
            double startY = y + Math.sin(angle) * r;

            double endX = x - Math.cos(angle) * r;
            double endY = y - Math.sin(angle) * r;

            canvas.drawLine((float)startX, (float)startY, (float)endX, (float)endY, paint);
        }
    }

    public void fireBullet(int numBullets) {
        bulletManager.addBullet(x,y - 5, numBullets);
    }
}
