package ballblastx.gamepackage;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Cloud
{
    float x;
    float y;
    float speed;

    private Cloud(float x, float y, float speed)
    {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void move()
    {
        x += speed;
    }

    public void onDraw(Canvas canvas, Paint paint)
    {
        canvas.drawBitmap();
    }
}
