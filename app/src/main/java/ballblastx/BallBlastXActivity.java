package ballblastx;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import ballblastx.enums.GameMode;
import ballblastx.gamepackage.Settings;
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
        requestFullScreen(view);
        this.setContentView(view);
    }

    private static void requestFullScreen(View view)
    {
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                ;

        view.setSystemUiVisibility(uiOptions);
    }

    public void writeSettings() {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput("BallBlastX_Settings.txt", Context.MODE_PRIVATE));

            outputStreamWriter.write("BestScore=" + Settings.bestScore + "|");

            outputStreamWriter.close();
        } catch (Exception e) {
        }
    }

    public void readSettings() {
        try {
            InputStream inputStream = this.openFileInput("BallBlastX_Settings.txt");

            if (inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();

                String result = stringBuilder.toString();
                applySettings(result);
            }
        }
        catch (Exception e) {
            writeSettings();
        }
    }

    private void applySettings(String settings)
    {
        String[] all = settings.split("\\|");

        for (String item : all)
        {
            if (item != null && item.length() > 1)
            {
                String[] details = item.split("\\=");

                if (details.length == 2)
                {
                    String name = details[0];

                    if (name.compareTo("BestScore") == 0)
                    {
                        Settings.bestScore = Integer.parseInt(details[1]);
                    }
                }
            }
        }
    }
}
