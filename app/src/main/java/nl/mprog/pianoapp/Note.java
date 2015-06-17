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
    private String noteName;
    final static int STEPS = 100;
    final static String TAG = "Note";
    FadeOutTimer fadeOutTimer, fadeInTimer;
    private String phase = "Idle";
    private Boolean playing = false, attackPhase = false, decayPhase = false, sustainPhase = false, releasePhase = false;


    public Note(Button button, int soundId, SoundPool sounds, String name)
    {
        trigger = button;
        id = soundId;
        soundPool = sounds;
        noteName = name;
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
        initialVolume = volume;
        stream = soundPool.play(id, initialVolume, initialVolume, 1, -1, 1f);
        playing = true;
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
        soundPool.setVolume(stream, volume, volume);
        initialVolume = volume;
    }

    public Button getTrigger ()
    {
        return trigger;
    }

    public boolean isPlaying()
    {
        return playing;
    }

    public String getNoteName()
    {
        return noteName;
    }

    public void attack(float targetVolume)
    {
        phase = "Attack";
        attackPhase = true;
        final long timeStep = (long)a/STEPS;
        final float volumeStep = targetVolume/STEPS;
        Log.d(TAG, "Timestep= " + timeStep);
        fadeInTimer = new FadeOutTimer(a, timeStep, volumeStep, initialVolume);
        fadeInTimer.start();
    }

    public void interrupt()
    {
        phase = "Idle";
        fadeOutTimer.cancel();
        Log.d(TAG, "timer canceled!");
    }

    public String getPhase()
    {
        return phase;
    }

    public void interruptAttack()
    {
        phase = "Idle";
        fadeInTimer.cancel();
        Log.d(TAG, "attack canceled!");
    }

    public void release()
    {
        phase = "Release";
        final long timeStep = (long)r/STEPS;
        final float volumeStep = initialVolume/STEPS;
        Log.d(TAG, "Timestep= " + timeStep);
        fadeOutTimer = new FadeOutTimer(r, timeStep, volumeStep, initialVolume);
        fadeOutTimer.start();



        // lower volume by a certain amount every step
        /*final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run()
            {
                soundPool.setVolume(stream, (initialVolume-(volumeStep*i)), (initialVolume-(volumeStep*i)));
                i++;
                Log.d(TAG, "volume= " + (initialVolume-(volumeStep*i)));
                Log.d(TAG, "iterator= " + i);
                handler.postDelayed(this, timeStep);
            }
        });
        soundPool.stop(stream);
        Log.d(TAG, "finished");*/
    }

    public int id()
    {
        return id;
    }

    public int getA()
    {
        return a;
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
            if (!phase.equals("Attack"))
            {
                soundPool.stop(stream);
            }
            Log.d(TAG, "Timer finished");
            finished = true;
            playing = false;
        }

        public Boolean checkFinished()
        {
            return finished;
        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            if (phase.equals("Attack"))
            {
                volume = volume + step;
            }
            else
            {
                volume = volume - step;
            }
            Log.d(TAG, "Volume = " +volume);
            soundPool.setVolume(stream, volume, volume);
        }
    }
}