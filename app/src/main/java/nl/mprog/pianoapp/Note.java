package nl.mprog.pianoapp;


import android.media.SoundPool;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;

public class Note
{
    private int a, d, s, r, id, stream;
    private Button trigger;
    private float initialVolume;
    private SoundPool soundPool;
    final static int STEPS = 100;
    final static String TAG = "Note";
    FadeOutTimer fadeOutTimer;
    private Boolean playing = false;
    int i = 0;


    public Note(Button button, int soundId, SoundPool sounds)
    {
        trigger = button;
        id = soundId;
        soundPool = sounds;

    }

    public void setEnvelope(int Attack, int Decay, int Sustain, int Release)
    {
        a = Attack;
        d = Decay;
        s = Sustain;
        r = Release;
    }



    public void play(float volume)
    {
        if (!playing)
        {
            initialVolume = volume;
            stream = soundPool.play(id, volume, volume, 1, -1, 1f);
            playing = true;
        }
        else
        {
            Log.d(TAG, "already playing!");
            soundPool.stop(stream);
            initialVolume = volume;
            stream = soundPool.play(id, volume, volume, 1, -1, 1f);
            playing = true;
        }

    }

    public void stop()
    {
        soundPool.stop(stream);
    }

    public void setPitch(float pitch)
    {
        soundPool.setRate(stream, pitch);
    }

    public void setVolume(float volume)
    {
        soundPool.setVolume(stream, volume, volume) ;
    }

    public Button getTrigger ()
    {
        return trigger;
    }

    public void release()
    {
        i = 0;
        final long timeStep = (long)r/STEPS;
        final float volumeStep = initialVolume/STEPS;
        Log.d(TAG, "Timestep= " + timeStep);
        /*fadeOutTimer = new FadeOutTimer(r, timeStep, volumeStep, initialVolume);
        fadeOutTimer.start();
        if (fadeOutTimer.checkFinished())
        {
            soundPool.stop(stream);
        }*/

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run()
            {
                soundPool.setVolume(id, (initialVolume-(volumeStep*i)), (initialVolume-(volumeStep*i)));
                i++;
                Log.d(TAG, "volume= " + (initialVolume-(volumeStep*i)));
                Log.d(TAG, "iterator= " + i);
                handler.postDelayed(this, timeStep);
            }
        });
        soundPool.stop(stream);
        Log.d(TAG, "finished");
    }

    public int id()
    {
        return id;
    }






    // timer class for gradual fading
    public class FadeOutTimer extends CountDownTimer
    {
        private float step, volume;
        public boolean finished;
        public FadeOutTimer(long startTime, long interval, float volumeStep, float initVolume)
        {
            super(startTime, interval);
            finished = false;
            step = volumeStep;
            volume = initVolume;
            Log.d("1", "Timer set");
        }

        @Override
        public void onFinish()
        {
            soundPool.stop(id);
            Log.d("1", "Timer finished");
            finished = true;
        }

        public Boolean checkFinished()
        {
            return finished;
        }


        @Override
        public void onTick(long millisUntilFinished)
        {

            soundPool.setVolume(id, volume, volume);
            volume = volume - step;
            Log.d("1", "volume = " + volume);
        }
    }
}
