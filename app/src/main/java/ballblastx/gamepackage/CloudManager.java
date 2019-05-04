package ballblastx.gamepackage;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;
import java.util.Set;

import ballblastx.BallBlastXActivity;
import ballblastx.views.LoadingView;

public class CloudManager
{
    final float speedLimit = BallBlastXActivity.instance.height / 500.0f;
    final int numClouds = 5;
    Cloud[] clouds;

    public CloudManager()
    {
        clouds = new Cloud[numClouds];

        InitClouds();
    }

    private void InitClouds()
    {
        for (int i = 0; i < numClouds; ++i)
            clouds[i] = RandomCloud(true);
    }

    private Cloud RandomCloud(boolean randomX)
    {
        Random random = Settings.getRandom();

        int type = random.nextInt(2);
        int width = LoadingView.cloudWidths[type];
        boolean fromRight = random.nextInt(2) == 0;

        int x = -width;
        int y = random.nextInt(BallBlastXActivity.instance.height / 2);
        int speed = random.nextInt((int)(speedLimit + 1)) + (int)(y / 100);

        if (fromRight)
        {
            x = BallBlastXActivity.instance.width;
            speed = -speed;
        }

        if (randomX)
        {
            x = random.nextInt(BallBlastXActivity.instance.width + width) - width;
        }

        Cloud cloud = new Cloud(x, y, speed, type);
        return cloud;
    }

    public void move()
    {
        for (int i = 0; i < numClouds; ++i)
            clouds[i].move();

        checkIfCompleted();
    }

    public void checkIfCompleted()
    {
        for (int i = 0; i < numClouds; ++i)
            if (clouds[i].x > BallBlastXActivity.instance.width || clouds[i].x < -LoadingView.cloudWidths[clouds[i].cloudType])
                clouds[i] = RandomCloud(false);
    }

    public void onDraw(Canvas canvas, Paint paint)
    {
        for (int i = 0; i < numClouds; ++i)
            clouds[i].onDraw(canvas, paint);
    }
}
