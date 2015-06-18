package nl.mprog.pianoapp;

import android.os.CountDownTimer;
import android.util.Log;

/**
 * Created by PC on 18-6-2015.
 */
public class LowFrequencyOscillator
{
    float pitchStep, currentPitch, lowerBound, upperBound;
    Note currentNote;
    private String phase = "down";
    final static int STEPS = 100;
    Long timeRate, timeStep;
    private boolean playing = false;
    LFOTimer downwardTimer, upwardTimer;

    public LowFrequencyOscillator()
    {
        Log.d("LFO", "Created");
    }
    public void setIntensity(float lower, float upper)
    {
        lowerBound = lower;
        upperBound = upper;
        currentPitch = upperBound - lowerBound;
        pitchStep = (currentPitch)/STEPS;
        Log.d("LFO", "Intensity" + pitchStep);
        Log.d("LFO", "Pitch" + currentPitch);
    }

    public void setRate(long rate)
    {
        timeRate = rate;
        timeStep = rate/STEPS;
    }

    public void startVibrato(Note note)
    {
        Log.d("LFO", "Vibrato start");
        currentNote = note;
        downWard();

    }


    public void stopVibrato(Note note)
    {
        Log.d("LFO", "Vibrato stop");
        currentNote = note;
        if (phase.equals("down"))
        stopDown();
        stopUp();
    }

    public void stopDown()
    {
        downwardTimer.cancel();
    }

    public void stopUp()
    {
        upwardTimer.cancel();
    }

    public void downWard()
    {
        phase = "down";
        Log.d("LFO", "Downward start");
        downwardTimer = new LFOTimer(timeRate, timeStep, pitchStep, upperBound, phase);
        downwardTimer.start();
    }

    public void upWard()
    {
        phase = "up";
        Log.d("LFO", "Upward start");
        upwardTimer = new LFOTimer(timeRate, timeStep, pitchStep, lowerBound, phase);
        upwardTimer.start();
    }

    public class LFOTimer extends CountDownTimer
    {
        private float pitchMove, mainPitch;
        private boolean countingDown = false;
        private String waveDirection = "up";
        public LFOTimer(long startTime, long interval, float timerStep, float pitch, String direction)
        {
            super(startTime, interval);
            countingDown = false;
            mainPitch = pitch;
            Log.d("LFO", "initialPitch = " + mainPitch);
            pitchMove = timerStep;
            waveDirection = direction;
        }

        @Override
        public void onFinish()
        {
            /*if (waveDirection.equals("down"))
            {
                upwardTimer = new LFOTimer(timeRate, timeStep, pitchStep, lowerBound, "up");
                upwardTimer.start();
            }
            else if (waveDirection.equals("up"))
            {
                downwardTimer = new LFOTimer(timeRate, timeStep, pitchStep, upperBound, "down");
                downwardTimer.start();
            }*/
            this.cancel();


        }

        public Boolean checkCountingDown()
        {
            return countingDown;
        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            if (waveDirection.equals("up"))
            {
                mainPitch = mainPitch + pitchMove;
                Log.d("LFO", "Going up!");
            }
            else
            {
                mainPitch = mainPitch - pitchMove;
                Log.d("LFO", "Going down!");
            }
            currentNote.setPitch(mainPitch);
            Log.d("LFO", "Pitch= " +mainPitch);

        }
    }
}
