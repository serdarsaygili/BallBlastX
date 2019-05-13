package ballblastx.libraries;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import ballblastx.BallBlastXActivity;
import ballblastx.gamepackage.Settings;

public class ImageContainer {
    public static Paint paint;
    public static Bitmap bullet;

    public static Paint getPaint() {
        if (paint == null) {
            paint = new Paint();

            paint.setAntiAlias(true);
            paint.setTextSize(Settings.debugTextSize);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setStrokeWidth(1);
            paint.setColor(Color.BLACK);
        }

        return paint;
    }

    public static int getColorRatio(int color, float ratio) {
        int alpha = color & 0xff000000;
        int red = (color & 0x00ff0000) >> 16;
        int green = (color & 0x0000ff00) >> 8;
        int blue = (color & 0x000000ff);

        int result = alpha + ((int)(red * ratio) << 16) + ((int)(green * ratio) << 8) + (int)(blue * ratio);
        return result;
    }

    public static void CreateBullet() {

        int w = Settings.bulletWidth;
        int h = Settings.bulletHeight;
        bullet = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        Canvas tempCanvas = new Canvas(bullet);
        Paint paint = getPaint();

        int center = w / 2;
        for (int i = 0; i < w; ++i)
        {
            int abs = Math.abs(center - i);
            int color = getColorRatio(0xffff5500, 2.f * abs / w);
            paint.setColor(color);
            tempCanvas.drawLine(i, abs, i, h, paint);
        }

        paint.setColor(0x88ff5500);
        tempCanvas.drawLine(0, h - 2, w, h - 2, paint);
    }
}
