package ballblastx.views;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import ballblastx.BallBlastXActivity;
import ballblastx.enums.GameMode;
import ballblastx.libraries.Helper;


public class SplashView extends View implements Runnable {

    Paint paint;

    public SplashView(BallBlastXActivity context) {
        super(context);
        start();
        paint = new Paint();
    }

    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.RED);

        canvas.drawBitmap(LoadingView.Splash, 0, 0, null);
        canvas.drawText(Integer.toString(splashStatus), 20, 20, paint);
    }

    int splashStatus;
    @Override
    public void run() {

        for (int i = 0; i < 3; i++) {
            splashStatus = i + 1;
            Helper.sleep(500);
            this.postInvalidate();
        }

        BallBlastXActivity.instance.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BallBlastXActivity.instance.setView(GameMode.Menu);
            }
        });
    }

    public void start()
    {
        Thread thread = new Thread(this);
        thread.start();
    }
}

