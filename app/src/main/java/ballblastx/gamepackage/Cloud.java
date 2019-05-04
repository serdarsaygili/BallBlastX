package ballblastx.gamepackage;

import android.graphics.Canvas;
import android.graphics.Paint;

import ballblastx.views.LoadingView;

public class Cloud
{
    float x;
    float y;
    float speed;
    int cloudType;

    public Cloud(float x, float y, float speed, int cloudType)
    {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.cloudType = cloudType;
    }

    public void move()
    {
        x += speed;
    }

    public void onDraw(Canvas canvas, Paint paint)
    {
        canvas.drawBitmap(LoadingView.clouds[cloudType], x, y, null);
    }
}
