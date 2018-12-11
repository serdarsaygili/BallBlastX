package ballblastx.gamepackage;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

public class BulletManager {
    List<Bullet> Bullets;
    Long lastBulletFireTime;

    public BulletManager() {
        Bullets = new ArrayList<Bullet>();
        lastBulletFireTime = System.currentTimeMillis();
    }

    public synchronized void addBullet(float x, float y) {
        if (System.currentTimeMillis() - lastBulletFireTime > 100) {
            Bullet bullet = new Bullet(x, y);
            Bullets.add(bullet);

            lastBulletFireTime = System.currentTimeMillis();
        }
    }

    public synchronized void removeBullets() {
        for (int i = Bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = Bullets.get(i);
            if (bullet.y < -5) {
                Bullets.remove(i);
            }
        }
    }

    public void moveBullets() {
        for (Bullet bullet : Bullets) {
            bullet.move();
        }
    }

    public synchronized void onDraw(Canvas canvas, Paint paint) {
        paint.setColor(0xff000000);
        paint.setStyle(Paint.Style.FILL);

        for (Bullet bullet : Bullets) {
            bullet.onDraw(canvas, paint);
        }
    }
}
