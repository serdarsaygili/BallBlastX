package ballblastx.gamepackage;

import android.graphics.Canvas;
import android.graphics.Paint;

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
            for (Ball ball : ballManager.balls) {
                double distance = Helper.getEuclideanDistance(x, y + Settings.bodyHeight / 2, ball.x, ball.y);

                if (distance < Settings.bodyWidth / 2 + ball.radius) {
                    return true;
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

        // block to be removed
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(isGameOver ? 0x88FF0000 : 0x88000000);
        canvas.drawCircle(x, y + Settings.bodyHeight / 2, Settings.bodyWidth / 2, paint);

        // wheels
        paint.setColor(0xff000000);
        paint.setStrokeWidth(Settings.bodyWheelRadius / 3);
        int wheelY = Settings.groundVerticalPositionY - Settings.bodyWheelRadius;
        canvas.drawCircle(x - Settings.bodyWidth / 2, wheelY, Settings.bodyWheelRadius, paint);
        canvas.drawCircle(x + Settings.bodyWidth / 2, wheelY, Settings.bodyWheelRadius, paint);

        // wheel arms
        DrawWheelArms(canvas, paint, x - Settings.bodyWidth / 2, wheelY, Settings.bodyWheelRadius);
        DrawWheelArms(canvas, paint, x + Settings.bodyWidth / 2, wheelY, Settings.bodyWheelRadius);

        paint.setStyle(Paint.Style.FILL);
        //
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
