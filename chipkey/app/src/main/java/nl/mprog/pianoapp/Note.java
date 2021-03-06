package nl.mprog.pianoapp;


import android.media.SoundPool;
import android.os.CountDownTimer;
import android.widget.Button;

public class Note
{
    private int a, d, r, id, stream;
    private Button trigger;
    private float initialVolume, s;
    private SoundPool soundPool;
    private String noteName;
    final static int STEPS = 100;
    FadeOutTimer fadeOutTimer, fadeInTimer, decayTimer;
    private String phase = "Idle";


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

    public String getNoteName()
    {
        return noteName;
    }


    // fade in current sound from 0 to target volume
    public void attack(float targetVolume)
    {
        phase = "Attack";
        final long timeStep = (long)a/STEPS;
        final float volumeStep = targetVolume/STEPS;
        initialVolume = targetVolume;
        fadeInTimer = new FadeOutTimer(a, timeStep, volumeStep, 0);
        fadeInTimer.start();
    }


    // fade out current sound from initial volume to sustain volume
    public void decay()
    {
        phase = "Decay";
        final long timeStep = (long)d/STEPS;
        final float volumeStep = (initialVolume-s)/STEPS;
        decayTimer = new FadeOutTimer(d, timeStep, volumeStep, initialVolume);
        decayTimer.start();
    }

    public void interrupt()
    {
        phase = "Idle";
        fadeOutTimer.cancel();

    }

    public String getPhase()
    {
        return phase;
    }

    public void interruptAttack()
    {
        phase = "Idle";
        fadeInTimer.cancel();

    }

    public void interruptDecay()
    {
        phase = "Idle";
        decayTimer.cancel();

    }

    // fade out current sound from initial volume to 0
    public void release()
    {
        phase = "Release";
        final long timeStep = (long)r/STEPS;
        final float volumeStep = initialVolume/STEPS;
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
            this.cancel();

            // stop playing sound after decay or release
            if (phase.equals("Release") || (phase.equals("Decay") && s == 0))
            {
                soundPool.stop(stream);
                phase = "Idle";
            }

            // instantly go to decay after attack phase if variables are significant
            else if (phase.equals("Attack") && d> 0 && s < volume)
            {
                phase = "Decay";
                final long timeStep = (long)d/STEPS;
                final float volumeStep = (volume-s)/STEPS;

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