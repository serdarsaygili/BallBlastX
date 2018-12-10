package ballblastx;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import ballblastx.enums.GameMode;
import ballblastx.views.GameView;
import ballblastx.views.LoadingView;
import ballblastx.views.MenuView;
import ballblastx.views.SplashView;

public class BallBlastXActivity  extends Activity {
    public GameMode mode = GameMode.Loading;
    public static BallBlastXActivity instance;
    public int width, height;

    public BallBlastXActivity() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;

        this.setView(mode);
    }

    public void setView(GameMode gameMode) {
        mode = gameMode;
        View view;

        switch (mode) {
            case Loading:
                view = new LoadingView(this);
                break;
            case Splash:
                view = new SplashView(this);
                break;
            case Game:
                view = new GameView(this);
                break;
            case About:
                view = new LoadingView(this);
                break;
            default:
                view = new MenuView(this);
        }
        this.setContentView(view);
    }
}
