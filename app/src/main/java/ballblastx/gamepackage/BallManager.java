package ballblastx.gamepackage;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import ballblastx.views.LoadingView;

public class BallManager {
    List<Ball> balls;
    private int totalCount;
    private int gameLevel;
    Long lastBallAddTime;
    BulletManager bulletManager;

    public BallManager(BulletManager bulletManager) {
        balls = new ArrayList<Ball>();
        this.bulletManager = bulletManager;
        lastBallAddTime = System.currentTimeMillis();
    }

    public void continueLevel() {
    }

    public void resetLevel(int level) {
        synchronized (balls) {
            balls.clear();
        }

        gameLevel = level;
        totalCount = level * 100;
    }

    public void addBall() {
        if (System.currentTimeMillis() - lastBallAddTime > Settings.ballAddingFrequency && totalCount > 0 &&
                balls.size() < 3 + Math.sqrt(gameLevel)) {
            int ballPoint = Settings.getRandom().nextInt(gameLevel * 100/4 + 1) + 1;
            totalCount -= ballPoint;

            synchronized (balls) {
                int ballIndex = Settings.getRandom().nextInt(4);
                int ballSize = LoadingView.ballSizes.get(ballIndex);
                Ball ball = new Ball(ballSize, ballIndex, ballPoint);
                balls.add(ball);
            }

            lastBallAddTime = System.currentTimeMillis();
        }

    }

    public int removeBall() {
        int totalHit = 0;

        synchronized (balls) {
            synchronized (bulletManager.Bullets) {
                for (int i = balls.size() - 1; i >= 0; i--) {
                    for (int j = bulletManager.Bullets.size() - 1; j >= 0; j--) {
                        if (Math.pow(Math.abs(balls.get(i).x - bulletManager.Bullets.get(j).x), 2) +
                                Math.pow(Math.abs(balls.get(i).y - bulletManager.Bullets.get(j).y), 2) <=
                                Math.pow(balls.get(i).radius + 2, 2)) {
                            bulletManager.Bullets.remove(j);
                            balls.get(i).count--;
                            ++totalHit;
                        }
                    }

                    if (balls.get(i).count <= 0) {
                        popBall(balls.get(i));
                        balls.remove(i);
                    }
                }
            }
        }

        return totalHit;
    }

    public void popBall(Ball ball) {
        if (ball.ballSizeIndex < 3) {
            int ballSizeIndex = ball.ballSizeIndex + 1;
            int ballSize = LoadingView.ballSizes.get(ball.ballSizeIndex + 1);
            Ball childBall1 = new Ball(ballSize, ballSizeIndex, ball.startCount / 2);
            Ball childBall2 = new Ball(ballSize, ballSizeIndex, ball.startCount / 2);
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

    public int moveBalls() {
        for (Ball ball : balls) {
            ball.move();
        }

        int totalHits = removeBall();
        return totalHits;
    }

    public boolean hasCompletedBalls() {
        return totalCount <= 0 && balls.size() == 0;
    }

    public void onDraw(Canvas canvas, Paint paint) {
        paint.setTextAlign(Paint.Align.CENTER);

        synchronized (balls) {
            for (Ball ball: balls) {
                ball.onDraw(canvas, paint);
            }
        }
    }
}
