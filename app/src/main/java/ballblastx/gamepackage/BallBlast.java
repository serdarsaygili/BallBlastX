package ballblastx.gamepackage;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import ballblastx.BallBlastXActivity;

public class BallBlast {
    float X, Y, R;
    int BlastColor;
    public int T;

    public BallBlast(float x, float y, float r, int color)
    {
        X = x;
        Y = y;
        R = r;
        BlastColor = color;
        T = 0;
    }

    public void move()
    {
        T += BallBlastXActivity.instance.height / 200;
    }

    public void onDraw(Canvas canvas, Paint paint)
    {
        int color = 0;
        if (T <= 50)
        {
            color = Color.argb(120 - 2 * T, Color.red(BlastColor), Color.green(BlastColor), Color.blue(BlastColor));
        }

        paint.setColor(color);
        canvas.drawCircle(X, Y, R + T, paint);

        if (T < 10)
        {
            canvas.drawLine(X - R + 2 * T, Y, X - R - 4 * T, Y, paint);
            canvas.drawLine(X + R - 2 * T, Y, X + R + 4 * T, Y, paint);

            canvas.drawLine(X, Y - R + 2 * T, X, Y - R - 4 * T, paint);
            canvas.drawLine(X, Y + R - 2 * T, X, Y + R + 4 * T, paint);
        }
    }
}
