package nl.mprog.pianoapp;


import android.media.SoundPool;
import android.os.CountDownTimer;
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
        soundPool.setRate(stream, pitch) ;
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
        long timeStep = (long)r/STEPS;
        float volumeStep = initialVolume/STEPS;
        fadeOutTimer = new FadeOutTimer(r, timeStep, id, volumeStep, initialVolume);
        fadeOutTimer.start();
        if (fadeOutTimer.checkFinished())
        {
            soundPool.stop(stream);
        }
    }

    public int id()
    {
        return id;
    }

    // timer class for gradual fading
    public class FadeOutTimer extends CountDownTimer
    {
        private int noteId;
        private float step, volume;
        public boolean finished;
        public FadeOutTimer(long startTime, long interval, int streamId, float volumeStep, float initVolume)
        {
            super(startTime, interval);
            finished = false;
            noteId = streamId;
            step = volumeStep;
            volume = initVolume;
            Log.d("1", "Timer set");
        }

        @Override
        public void onFinish()
        {
            soundPool.stop(noteId);
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

            soundPool.setVolume(noteId, volume, volume);
            volume = volume - step;

            Log.d("1", "volume = " + volume);
        }
    }
}
