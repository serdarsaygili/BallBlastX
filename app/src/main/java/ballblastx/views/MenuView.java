package ballblastx.views;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import ballblastx.BallBlastXActivity;
import ballblastx.enums.GameMode;

public class MenuView extends View {

    Paint paint;

    public MenuView(BallBlastXActivity context) {
        super(context);

        paint = new Paint();
    }

    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.RED);

        canvas.drawText("Menu", 50, 50, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();

        switch(action) {
            case MotionEvent.ACTION_DOWN:
                BallBlastXActivity.instance.setView(GameMode.Game);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }
}
