package ballblastx.gamepackage;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

public class BallManager {
    List<Ball> balls;
    private int totalCount;
    private int gameLevel;
    private static final int smallBall = 25;
    private static final int mediumBall = 50;
    private static final int bigBall = 75;
    Long lastBallAddTime;
    BulletManager bulletManager;

    public BallManager(BulletManager bulletManager, int gameLevel) {
        balls = new ArrayList<Ball>();
        totalCount = gameLevel * 100;
        this.gameLevel = gameLevel;
        this.bulletManager = bulletManager;
        lastBallAddTime = System.currentTimeMillis();
    }

    private int getRandomBallSize() {
        return (Settings.getRandom().nextInt(3) + 1) * Settings.ballSizeCaliber;
    }

    public void addBall() {
        if (System.currentTimeMillis() - lastBallAddTime > Settings.ballAddingFrequency && totalCount > 0 &&
                balls.size() < 3 + Math.sqrt(gameLevel)
                && balls.size() < 1
                ) {
            int ballPoint = Settings.getRandom().nextInt(gameLevel * 100/4 + 1) + 1;
            totalCount -= ballPoint;
            Ball ball = new Ball(getRandomBallSize(), ballPoint);
            balls.add(ball);

            lastBallAddTime = System.currentTimeMillis();
        }

    }

    public void removeBall() {
        for (int i = balls.size() - 1; i >= 0; i--) {
            for (int j = bulletManager.Bullets.size() - 1; j >= 0; j--) {
                if (Math.pow(Math.abs(balls.get(i).x - bulletManager.Bullets.get(j).x), 2) +
                        Math.pow(Math.abs(balls.get(i).y - bulletManager.Bullets.get(j).y), 2) <=
                        Math.pow(balls.get(i).radius + 2, 2)) {
                    bulletManager.Bullets.remove(j);
                    balls.get(i).count--;
                }
            }

            if (balls.get(i).count <= 0) {
                popBall(balls.get(i));
                balls.remove(i);
            }
        }
    }

    public void popBall(Ball ball) {
        if (ball.radius >= 2 * Settings.ballSizeCaliber) {
            Ball childBall1 = new Ball(((int) ball.radius - Settings.ballSizeCaliber), ball.startCount / 2);
            Ball childBall2 = new Ball(((int) ball.radius - Settings.ballSizeCaliber), ball.startCount / 2);
            childBall1.x = ball.x;
            childBall1.y = ball.y;
            childBall1.velocityY = -10;
            childBall1.velocityX = ball.velocityX;
            childBall1.startY = ball.startY;
            childBall1.isGravityStarted = true;

            childBall2.x = ball.x;
            childBall2.y = ball.y;
            childBall2.velocityY = -10;
            childBall2.velocityX = -ball.velocityX;
            childBall2.startY = ball.startY;
            childBall2.isGravityStarted = true;

            balls.add(childBall1);
            balls.add(childBall2);
        }
    }

    public void moveBalls() {
        for (Ball ball : balls) {
            ball.move();
        }

        removeBall();
    }

    public void onDraw(Canvas canvas, Paint paint) {
        for (Ball ball: balls) {
            ball.onDraw(canvas, paint);
        }
    }
}
