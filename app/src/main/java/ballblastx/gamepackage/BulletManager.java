package ballblastx.gamepackage;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class BulletManager {
    List<Bullet> Bullets;
    Long lastBulletFireTime;
    ReentrantLock lock = new ReentrantLock();

    public BulletManager() {
        Bullets = new ArrayList<Bullet>();
        lastBulletFireTime = System.currentTimeMillis();
    }

    public void continueLevel() {
        lastBulletFireTime = System.currentTimeMillis();
    }

    public void resetLevel(int level) {
        Bullets.clear();
    }

    public void addBullet(float x, float y, int numBullets) {
        if (System.currentTimeMillis() - lastBulletFireTime > 100) {

            float leftStart = (1 - numBullets) * Settings.bulletWidth;
            float addWidth = 2 * Settings.bulletWidth;

            synchronized (Bullets) {
                for (int i = 0; i < numBullets; ++i) {
                    Bullet bullet = new Bullet(x + leftStart + i * addWidth, y);
                    Bullets.add(bullet);
                }
            }

            lastBulletFireTime = System.currentTimeMillis();
        }
    }

    public void removeBullets() {
        synchronized (Bullets) {
            for (int i = Bullets.size() - 1; i >= 0; i--) {
                Bullet bullet = Bullets.get(i);
                if (bullet.y < -5) {
                    Bullets.remove(i);
                }
            }
        }
    }

    public void moveBullets() {
        synchronized (Bullets) {
            for (Bullet bullet : Bullets) {
                bullet.move();
            }
        }
    }

    public void onDraw(Canvas canvas, Paint paint) {
        paint.setColor(0xffffff00);
        //paint.setStyle(Paint.Style.FILL);

        synchronized (Bullets) {
            for (Bullet bullet : Bullets) {
                bullet.onDraw(canvas, paint);
            }
        }

        /*paint.setColor(0xffff8800);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);

        synchronized (Bullets) {
            for (Bullet bullet : Bullets) {
                bullet.onDraw(canvas, paint);
            }
        }

        paint.setStyle(Paint.Style.FILL);*/
    }
}
