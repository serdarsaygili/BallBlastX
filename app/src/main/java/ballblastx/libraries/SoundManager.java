package ballblastx.libraries;

import android.media.AudioManager;
import android.media.MediaPlayer;

import ballblastx.BallBlastXActivity;
import ballblastx.R;
import ballblastx.enums.Sound;

public class SoundManager
{
    private static MediaPlayer mpLoop;
    private static MediaPlayer mp;

    public static void StartLoop() {

        if (mpLoop == null)
        {
            mpLoop = MediaPlayer.create(BallBlastXActivity.instance, R.raw.loop);
            mpLoop.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mpLoop.setLooping(true);
            mpLoop.setVolume(10, 10);
        }

        mpLoop.start();
    }

    public static void PauseLoop()
    {
        if (mpLoop != null && mpLoop.isPlaying())
        {
            mpLoop.pause();
        }
    }

    public static void StopLoop()
    {
        if (mpLoop != null)
        {
            if (mpLoop.isPlaying())
            {
                mpLoop.stop();
            }

            mpLoop.release();
            mpLoop = null;
        }
    }

    private static int GetSound(Sound sound)
    {
        int result = 0;

        switch (sound)
        {
            case GameOver: result = R.raw.fail; break;
            case Win: result = R.raw.win; break;
            case Completed: result = R.raw.completed; break;
            case Pat: result = R.raw.pat; break;
        }

        return result;
    }

    public static void Play(Sound sound)
    {
        try {
            if (mp != null)
            {
                if (mp.isPlaying())
                    mp.stop();

                mp.release();
            }

            int id = GetSound(sound);
            mp = MediaPlayer.create(BallBlastXActivity.instance, id);
            mp.setVolume(30, 30);
            mp.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void StopOthers()
    {
        if (mp != null)
        {
            if (mp.isPlaying())
                mp.stop();

            mp.release();
            mp = null;
        }
    }
}
