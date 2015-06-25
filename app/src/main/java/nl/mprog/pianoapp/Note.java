package nl.mprog.pianoapp;


import android.media.SoundPool;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;

public class Note
{
    private int a, d, r, id, stream;
    private Button trigger;
    private float initialVolume, s;
    private SoundPool soundPool;
    private String noteName;
    final static int STEPS = 100;
    final static String TAG = "Note";
    FadeOutTimer fadeOutTimer, fadeInTimer, decayTimer;
    private String phase = "Idle";
    private Boolean playing = false;


    public Note(Button button, int soundId, SoundPool sounds, String name)
    {
        trigger = button;
        id = soundId;
        soundPool = sounds;
        noteName = name;
    }

    public void setEnvelope(int Attack, int Decay, float Sustain, int Release)
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
        final long timeStep = (long)a/STEPS;
        final float volumeStep = targetVolume/STEPS;
        Log.d(TAG, "Attack Timestep= " + timeStep);
        initialVolume = targetVolume;
        fadeInTimer = new FadeOutTimer(a, timeStep, volumeStep, 0);
        fadeInTimer.start();
    }

    public void decay()
    {
        phase = "Decay";
        final long timeStep = (long)d/STEPS;
        final float volumeStep = (initialVolume-s)/STEPS;
        Log.d(TAG, "Decay Timestep= " + timeStep);
        decayTimer = new FadeOutTimer(d, timeStep, volumeStep, initialVolume);
        decayTimer.start();
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

    public void interruptDecay()
    {
        phase = "Idle";
        decayTimer.cancel();
        Log.d(TAG, "decay canceled!");
    }

    public void release()
    {
        phase = "Release";
        final long timeStep = (long)r/STEPS;
        final float volumeStep = initialVolume/STEPS;
        Log.d(TAG, "Timestep= " + timeStep);
        fadeOutTimer = new FadeOutTimer(r, timeStep, volumeStep, initialVolume);
        fadeOutTimer.start();
    }

    public int id()
    {
        return id;
    }

    public int getA()
    {
        return a;
    }
    public int getD() {return d;}

    public float getS()
    {
        return s;
    }

    public int getR(){return r;}

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
        }

        @Override
        public void onFinish()
        {
            Log.d(TAG,"Timer finished");
            this.cancel();

            // stop playing after decay or release
            if (phase.equals("Release") || (phase.equals("Decay") && s == 0))
            {
                soundPool.stop(stream);
                playing = false;
                phase = "Idle";
            }

            // instantly go to decay after attack phase if variables are significant
            else if (phase.equals("Attack") && d> 0 && s < volume)
            {
                phase = "Decay";
                final long timeStep = (long)d/STEPS;
                final float volumeStep = (volume-s)/STEPS;
                Log.d(TAG, "Decay Timestep= " + timeStep);
                decayTimer = new FadeOutTimer(d, timeStep, volumeStep, volume);
                decayTimer.start();
            }
        }

        // update note volume every tick
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
            soundPool.setVolume(stream, volume, volume);
        }
    }
}