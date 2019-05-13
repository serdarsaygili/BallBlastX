package ballblastx.gamepackage;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ballblastx.BallBlastXActivity;

public class BallBlastManager {
    List<BallBlast> Blasts;

    public BallBlastManager()
    {
        Blasts = new ArrayList<BallBlast>();
    }

    public void Add(float x, float y, float r)
    {
        synchronized (Blasts)
        {
            Random rand = Settings.getRandom();
            int rn = BallBlastXActivity.instance.width / 50;
            int colors[] = new int[] {0xffff0000, 0xff000000, 0xffff0000, 0xff000000, 0xffff0000, 0xff000000};

            for (int i = 0; i < colors.length; ++i)
            {
                BallBlast blast = new BallBlast(x + rand.nextInt(2 * rn) - rn, y + rand.nextInt(2 * rn) - rn, r + rand.nextInt(2 * rn) - rn, colors[i]);
                Blasts.add(blast);
            }

            RemoveFinished();
        }
    }

    public void move()
    {
        synchronized (Blasts)
        {
            for (BallBlast blast : Blasts)
            {
                blast.move();
            }
        }
    }

    public void RemoveFinished()
    {
        int count = Blasts.size();
        for (int i = count - 1; i >= 0; --i)
        {
            BallBlast blast = Blasts.get(i);
            if (blast.T > 60)
                Blasts.remove(i);
        }
    }

    public void onDraw(Canvas canvas, Paint paint)
    {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);

        synchronized (Blasts)
        {
            for (BallBlast blast : Blasts)
            {
                blast.onDraw(canvas, paint);
            }
        }
    }
}
