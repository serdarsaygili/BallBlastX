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
import ballblastx.gamepackage.Settings;
import ballblastx.libraries.Helper;
import ballblastx.libraries.ImageContainer;

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

        if (BallBlastXActivity.instance.width > 100 && BallBlastXActivity.instance.height > 0) {
            isDrawn = true;
        }
    }

    int status = 0;

    @Override
    public void run() {
        status = 0;

        while (!isDrawn) {
            Helper.sleep(200);
            this.postInvalidate();
        }

        status = 3; // Setting configuration
        this.postInvalidate();
        Helper.sleep(500);

        Settings.setConfiguration(BallBlastXActivity.instance.width, BallBlastXActivity.instance.height);
        BallBlastXActivity.instance.readSettings();

        status = 2; // Loading Images
        this.postInvalidate();
        Helper.sleep(200);

        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.splash);
        Splash = Bitmap.createScaledBitmap(bMap, BallBlastXActivity.instance.width, BallBlastXActivity.instance.height, true);
        ImageContainer.CreateBullet();

        status = 1; // End of loading
        this.postInvalidate();
        Helper.sleep(200);

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
