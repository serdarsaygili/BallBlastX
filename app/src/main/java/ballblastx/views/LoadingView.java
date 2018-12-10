package ballblastx.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import ballblastx.BallBlastXActivity;
import ballblastx.R;
import ballblastx.enums.GameMode;
import ballblastx.libraries.Helper;

public class LoadingView extends View implements Runnable {

    Paint paint;
    public static Bitmap Splash;
    private boolean isDrawn = false;

    public LoadingView(BallBlastXActivity context) {
        super(context);

        paint = new Paint();
        start();
    }


    @Override
    public void onDraw(Canvas canvas) {
        BallBlastXActivity.instance.width = (int)canvas.getWidth();
        BallBlastXActivity.instance.height = (int)canvas.getHeight();
        paint.setColor(Color.RED);

        canvas.drawText("Loading " + status, 50, 50, paint);
        isDrawn = true;
    }

    int status = 0;

    @Override
    public void run() {
        status = 0;
        while (!isDrawn) {
            Helper.sleep(200);
            this.postInvalidate();
        }

        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.splash);
        Splash = Bitmap.createScaledBitmap(bMap, BallBlastXActivity.instance.width, BallBlastXActivity.instance.height, true);

        for (int i = 1; i <= 10; i++) {
            status = 10 * i;
            this.postInvalidate();
            Helper.sleep(200);
        }

        this.postInvalidate(); //this function calls draw method
        BallBlastXActivity.instance.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BallBlastXActivity.instance.setView(GameMode.Splash);
            }
        });
    }

    public void start()
    {
        Thread thread = new Thread(this);
        thread.start();
    }
}
