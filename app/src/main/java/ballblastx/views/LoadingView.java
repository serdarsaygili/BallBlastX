package ballblastx.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ballblastx.BallBlastXActivity;
import ballblastx.R;
import ballblastx.enums.GameMode;
import ballblastx.gamepackage.Settings;
import ballblastx.libraries.Helper;
import ballblastx.libraries.ImageContainer;

public class LoadingView extends View implements Runnable {

    Paint paint;
    public static Bitmap Splash, ground;
    public static Bitmap clouds[];
    public static int cloudWidths[];
    public static List <Bitmap> Balls;
    public static List <Integer> ballSizes;
    public static List <Integer> maxVelocityYs;
    private boolean isDrawn = false;
    public static int groundCorrectionHeight;

    public LoadingView(BallBlastXActivity context) {
        super(context);

        paint = new Paint();
        Balls = new ArrayList<Bitmap>();
        ballSizes = new ArrayList<Integer>();
        clouds = new Bitmap[2];
        cloudWidths = new int[2];
        maxVelocityYs = new ArrayList<Integer>();
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

        int screenWidth = BallBlastXActivity.instance.width;
        int screenHeight = BallBlastXActivity.instance.height;

        status = 3; // Setting configuration
        this.postInvalidate();
        Helper.sleep(500);

        Settings.setConfiguration(screenWidth, screenHeight);
        BallBlastXActivity.instance.readSettings();

        status = 2; // Loading Images
        this.postInvalidate();
        Helper.sleep(200);

        ImageContainer.CreateBullet();
        loadImages(screenWidth, screenHeight);

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

    private void loadImages(int screenWidth, int screenHeight)
    {
        Bitmap tmp = BitmapFactory.decodeResource(getResources(), R.drawable.splash);
        Splash = Bitmap.createScaledBitmap(tmp, screenWidth, screenHeight, true);
        int ballSize = Settings.maxBallSize;

        tmp = BitmapFactory.decodeResource(getResources(), R.drawable.ball1);
        Balls.add(Bitmap.createScaledBitmap(tmp, ballSize, ballSize, true));
        tmp = BitmapFactory.decodeResource(getResources(), R.drawable.ball2);
        Balls.add(Bitmap.createScaledBitmap(tmp, ballSize, ballSize, true));
        tmp = BitmapFactory.decodeResource(getResources(), R.drawable.ball3);
        Balls.add(Bitmap.createScaledBitmap(tmp, ballSize, ballSize, true));
        tmp = BitmapFactory.decodeResource(getResources(), R.drawable.ball4);
        Balls.add(Bitmap.createScaledBitmap(tmp, ballSize, ballSize, true));
        tmp = BitmapFactory.decodeResource(getResources(), R.drawable.ball5);
        Balls.add(Bitmap.createScaledBitmap(tmp, ballSize, ballSize, true));
        tmp = BitmapFactory.decodeResource(getResources(), R.drawable.ball6);
        Balls.add(Bitmap.createScaledBitmap(tmp, ballSize, ballSize, true));
        tmp = BitmapFactory.decodeResource(getResources(), R.drawable.ball7);
        Balls.add(Bitmap.createScaledBitmap(tmp, ballSize, ballSize, true));
        tmp = BitmapFactory.decodeResource(getResources(), R.drawable.ball8);
        Balls.add(Bitmap.createScaledBitmap(tmp, ballSize, ballSize, true));
        tmp = BitmapFactory.decodeResource(getResources(), R.drawable.ball9);
        Balls.add(Bitmap.createScaledBitmap(tmp, ballSize, ballSize, true));
        tmp = BitmapFactory.decodeResource(getResources(), R.drawable.ball10);
        Balls.add(Bitmap.createScaledBitmap(tmp, ballSize, ballSize, true));

        cloudWidths[0] = screenWidth / 3;
        cloudWidths[1] = screenWidth / 2;

        tmp = BitmapFactory.decodeResource(getResources(), R.drawable.cloud1);
        clouds[0] = Bitmap.createScaledBitmap(tmp, cloudWidths[0], cloudWidths[0] * tmp.getHeight() / tmp.getWidth(), true);

        tmp = BitmapFactory.decodeResource(getResources(), R.drawable.cloud2);
        clouds[1] = Bitmap.createScaledBitmap(tmp, cloudWidths[1], cloudWidths[1] * tmp.getHeight() / tmp.getWidth(), true);

        tmp = BitmapFactory.decodeResource(getResources(), R.drawable.ground); //image should be 1000 * 100, our phone is 480X 800
        ground = Bitmap.createScaledBitmap(tmp, screenWidth, screenWidth * tmp.getHeight() / tmp.getWidth(), true);
        groundCorrectionHeight = 60 * tmp.getHeight() / tmp.getWidth();

        int ballCount = Balls.size();
        ballSizes.add(ballSize);
        for (int i = 0; i < 4; i++) {
            ballSize = ballSize * 3/4;
            ballSizes.add(ballSize);
            for (int j = 0; j < ballCount; j++) {
                Balls.add(Bitmap.createScaledBitmap(Balls.get(i), ballSize, ballSize, true));
            }
        }
    }
}
