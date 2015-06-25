package nl.mprog.pianoapp;

import android.os.CountDownTimer;
import android.util.Log;

import java.util.ArrayList;


// generates values corresponding to the shape of a triangular wave
public class LowFrequencyOscillator
{
    float pitchStep, currentPitch, lowerBound, upperBound;
    ArrayList<Note> noteList = new ArrayList<>();
    private String phase = "down";
    final static int STEPS = 100;
    Long timeRate, timeStep;
    private boolean playing = false;
    LFOTimer downwardTimer, upwardTimer;

    public LowFrequencyOscillator(){}


    // set amplitude of generated wave
    public void setIntensity(float lower, float upper)
    {
        lowerBound = lower;
        upperBound = upper;
        currentPitch = upperBound - lowerBound;
        pitchStep = (currentPitch)/STEPS;
    }

    // set rate of generated wave
    public void setRate(long rate)
    {
        timeRate = rate;
        timeStep = rate/STEPS;
    }


    public void startVibrato(Note note)
    {
        Log.d("LFO", "Vibrato start");
        noteList.add(note);
        Log.d("LFO", "Size of list = " + noteList.size());

        //start generating new LFO values only if it's the first note
        if (noteList.size() == 1)
        {
            downWard();
        }

    }


    public void stopVibrato(Note note)
    {
        Log.d("LFO", "Vibrato stop");
        noteList.remove(note);

        // stop running LFO when no notes are playing
        if (noteList.isEmpty())
        {
            stopDown();
            stopUp();
        }
    }

    public void stopDown()
    {
        downwardTimer.cancel();
    }

    public void stopUp()
    {
        upwardTimer.cancel();
    }


    // start timer to simulate downward motion of wave
    public void downWard()
    {
        phase = "down";
        downwardTimer = new LFOTimer(timeRate, timeStep, pitchStep, upperBound, phase);
        downwardTimer.start();
    }


    public class LFOTimer extends CountDownTimer
    {
        private float pitchMove, mainPitch;
        private String waveDirection = "up";
        public LFOTimer(long startTime, long interval, float timerStep, float pitch, String direction)
        {
            super(startTime, interval);
            mainPitch = pitch;
            pitchMove = timerStep;
            waveDirection = direction;
        }

        @Override
        public void onFinish()
        {
            // start timer in the opposite direction when finished
            this.cancel();
            if (waveDirection.equals("down"))
            {
                waveDirection = "up";
                upwardTimer = new LFOTimer(timeRate, timeStep, pitchStep, lowerBound, "up");
                upwardTimer.start();
            }
            else if (waveDirection.equals("up"))
            {
                waveDirection = "down";
                downwardTimer = new LFOTimer(timeRate, timeStep, pitchStep, upperBound, "down");
                downwardTimer.start();
            }

        }

        // update note pitch every tick
        @Override
        public void onTick(long millisUntilFinished)
        {
            if (waveDirection.equals("up"))
            {
                mainPitch = mainPitch + pitchMove;
            }
            else
            {
                mainPitch = mainPitch - pitchMove;
            }

            // set pitch for every note currently playing
            for (int i= 0; i< noteList.size(); i++)
            {
                noteList.get(i).setPitch(mainPitch);

            }

        }
    }
}
