package nl.mprog.pianoapp;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.audiofx.PresetReverb;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class MainActivity extends Activity implements View.OnTouchListener {

    private Button buttonC2, buttonCSharp2, buttonD2, buttonDSharp2, buttonE2, buttonF2, buttonFSharp2, buttonG2, buttonGSharp2, buttonA2, buttonASharp2, buttonB2, buttonC3, buttonCSharp3, buttonD3, buttonDSharp3, buttonE3,
    buttonF3, buttonFSharp3, buttonG3, buttonGSharp3, buttonA3, buttonASharp3, buttonB3, buttonC4, buttonCSharp4, buttonD4, buttonDSharp4, buttonE4, buttonF4, buttonFSharp4, buttonG4, buttonGSharp4, buttonA4, buttonASharp4, buttonB4, buttonC5;
    private String aftSelect = "None", modSelect = "Release";
    private int releaseTime = 0, attackTime = 0, decayTime = 0, vRate = 150;
    private float sustain = 0.5f;
    private boolean vibrato = false;
    private ArrayList<Integer> noteIdList;
    private ArrayList<Note> noteList;
    private ArrayList<Button> buttonList;
    private SoundBank soundBank;
    private SoundPool soundPool;
    private Note c2, cSharp2, d2, dSharp2, e2, f2, fSharp2, g2, gSharp2, a2, aSharp2, b2, c3, cSharp3, d3, dSharp3, e3, f3, fSharp3, g3, gSharp3, a3, aSharp3, b3, c4, cSharp4, d4, dSharp4, e4, f4, fSharp4, g4, gSharp4, a4, aSharp4, b4, c5;
    private LowFrequencyOscillator lfo;

    private static final int SEEKBAR_MIN = 1, SEEKBAR_MAX = 5000, MINIMUM_VALUE = 10, ENV_MULTIPLIER = 1000;
    private static final float DEF_INTENSITY_MIN = 0.97f , DEF_INTENSITY_MAX = 1.03f;
    private static final double LOG_MIN = 0.0, LOG_MAX = 3.69897;

    public static String PACKAGE_NAME, TAG = "Main";






    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PACKAGE_NAME = getApplicationContext().getPackageName();

        lfo = new LowFrequencyOscillator();
        lfo.setIntensity(DEF_INTENSITY_MIN, DEF_INTENSITY_MAX);
        lfo.setRate(vRate);
        soundBank = new SoundBank(getApplicationContext());
        soundBank.setInstrument("square");
        soundBank.loadSounds(PACKAGE_NAME);
        noteIdList = soundBank.getIdList();
        soundPool = soundBank.getSoundPool();
        initButtons();
        initNotes();
        modWheelSetup();

    }

    private float logScale(float value)
    {
        //make a logarithmic scale based on value from seekBar
        return(float) (LOG_MIN+(value-SEEKBAR_MIN)*(LOG_MAX - LOG_MIN)/(SEEKBAR_MAX-SEEKBAR_MIN));
    }



    //set up seekBar
    public void modWheelSetup()
    {
        SeekBar modWheel = (SeekBar)findViewById(R.id.seekBar);
        modWheel.setMax (SEEKBAR_MAX);

        modWheel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                float temp = seekBar.getProgress();
                if (modSelect.equals("Attack"))
                {
                    attackTime = (int) (ENV_MULTIPLIER * logScale(temp));
                }
                else if (modSelect.equals("Release"))
                {
                    releaseTime = (int) (ENV_MULTIPLIER * logScale(temp));
                }
                else if (modSelect.equals("Decay"))
                {
                    decayTime = (int) (ENV_MULTIPLIER * logScale(temp));
                }

                else if (modSelect.equals("Vibrato Rate"))
                {
                    vRate = 350 - (int)(70 * logScale(temp));
                    Log.d(TAG, "Vibrato Rate = " + vRate);
                    lfo.setRate(vRate);
                }

            }
        });

    }


    // go to fx Activity
    public void onEffectsStart(View v)
    {

        Intent i = new Intent(this, FXActivity.class);
        i.putExtra("aftSelect", aftSelect);
        i.putExtra("modSelect", modSelect);
        i.putExtra("vibrato", vibrato);
        startActivityForResult(i, 1);
    }


    private void register(int buttonResourceId)
    {
        findViewById(buttonResourceId).setOnTouchListener(this);
    }


    // assign buttons to onTouchListener
    public void initButtons()
    {

        noteIdList = new ArrayList<>(Arrays.asList(R.id.c2, R.id.cSharp2, R.id.d2, R.id.dSharp2, R.id.e2, R.id.f2,
        R.id.fSharp2, R.id.g2, R.id.gSharp2, R.id.a2, R.id.aSharp2, R.id.b2, R.id.c3, R.id.cSharp3, R.id.d3,
        R.id.dSharp3, R.id.e3, R.id.f3, R.id.fSharp3, R.id.g3, R.id.gSharp3, R.id.a3, R.id.aSharp3, R.id.b3, R.id.c4, R.id.cSharp4, R.id.d4,
        R.id.dSharp4, R.id.e4, R.id.f4, R.id.fSharp4, R.id.g4, R.id.gSharp4, R.id.a4, R.id.aSharp4, R.id.b4, R.id.c5));

        buttonC2 = (Button) findViewById(R.id.c2);
        buttonCSharp2 = (Button) findViewById(R.id.cSharp2);
        buttonD2 = (Button) findViewById(R.id.d2);
        buttonDSharp2 = (Button) findViewById(R.id.dSharp2);
        buttonE2 = (Button) findViewById(R.id.e2);
        buttonF2 = (Button) findViewById(R.id.f2);
        buttonFSharp2 = (Button) findViewById(R.id.fSharp2);
        buttonG2 = (Button) findViewById(R.id.g2);
        buttonGSharp2 = (Button) findViewById(R.id.gSharp2);
        buttonA2 = (Button) findViewById(R.id.a2);
        buttonASharp2 = (Button) findViewById(R.id.aSharp2);
        buttonB2 = (Button) findViewById(R.id.b2);
        buttonC3 = (Button) findViewById(R.id.c3);
        buttonCSharp3 = (Button) findViewById(R.id.cSharp3);
        buttonD3 = (Button) findViewById(R.id.d3);
        buttonDSharp3 = (Button) findViewById(R.id.dSharp3);
        buttonE3 = (Button) findViewById(R.id.e3);
        buttonF3 = (Button) findViewById(R.id.f3);
        buttonFSharp3 = (Button) findViewById(R.id.fSharp3);
        buttonG3 = (Button) findViewById(R.id.g3);
        buttonGSharp3 = (Button) findViewById(R.id.gSharp3);
        buttonA3 = (Button) findViewById(R.id.a3);
        buttonASharp3 = (Button) findViewById(R.id.aSharp3);
        buttonB3 = (Button) findViewById(R.id.b3);
        buttonC4 = (Button) findViewById(R.id.c4);
        buttonCSharp4 = (Button) findViewById(R.id.cSharp4);
        buttonD4 = (Button) findViewById(R.id.d4);
        buttonDSharp4 = (Button) findViewById(R.id.dSharp4);
        buttonE4 = (Button) findViewById(R.id.e4);
        buttonF4 = (Button) findViewById(R.id.f4);
        buttonFSharp4 = (Button) findViewById(R.id.fSharp4);
        buttonG4 = (Button) findViewById(R.id.g4);
        buttonGSharp4 = (Button) findViewById(R.id.gSharp4);
        buttonA4 = (Button) findViewById(R.id.a4);
        buttonASharp4 = (Button) findViewById(R.id.aSharp4);
        buttonB4 = (Button) findViewById(R.id.b4);
        buttonC5 = (Button) findViewById(R.id.c5);

        buttonList = new ArrayList<>(Arrays.asList(buttonC2, buttonCSharp2, buttonD2, buttonDSharp2, buttonE2,
        buttonF2, buttonFSharp2, buttonG2, buttonGSharp2, buttonA2, buttonASharp2, buttonB2, buttonC3, buttonCSharp3, buttonD3,
        buttonDSharp3, buttonE3, buttonF3, buttonFSharp3, buttonG3, buttonGSharp3, buttonA3, buttonASharp3, buttonB3, buttonC4,
        buttonCSharp4, buttonD4, buttonDSharp4, buttonE4, buttonF4, buttonFSharp4, buttonG4, buttonGSharp4, buttonA4, buttonASharp4, buttonB4, buttonC5));


        for (int i = 0; i < noteIdList.size(); i++)
        {
            register(noteIdList.get(i));
        }

    }


    // initialize note objects
    public void initNotes()
    {
        c2 = new Note(buttonList.get(0), soundBank.c2, soundPool, "c2");
        cSharp2 = new Note(buttonList.get(1), soundBank.cSharp2, soundPool, "cSharp2");
        d2 = new Note(buttonList.get(2), soundBank.d2, soundPool, "d2");
        dSharp2 = new Note(buttonList.get(3), soundBank.dSharp2, soundPool, "dSharp2");
        e2 = new Note(buttonList.get(4), soundBank.e2, soundPool, "e2");
        f2 = new Note(buttonList.get(5), soundBank.f2, soundPool, "f2");
        fSharp2 = new Note(buttonList.get(6), soundBank.fSharp2, soundPool, "fSharp2");
        g2 = new Note(buttonList.get(7), soundBank.g2, soundPool, "g2");
        gSharp2 = new Note(buttonList.get(8), soundBank.gSharp2, soundPool, "gSharp2");
        a2 = new Note(buttonList.get(9), soundBank.a2, soundPool, "a2");
        aSharp2 = new Note(buttonList.get(10), soundBank.aSharp2, soundPool, "aSharp2");
        b2 = new Note(buttonList.get(11), soundBank.b2, soundPool, "b2");
        c3 = new Note(buttonList.get(12), soundBank.c3, soundPool, "c3");
        cSharp3 = new Note(buttonList.get(13), soundBank.cSharp3, soundPool, "cSharp3");
        d3 = new Note(buttonList.get(14), soundBank.d3, soundPool, "d3");
        dSharp3 = new Note(buttonList.get(15), soundBank.dSharp3, soundPool, "dSharp3");
        e3 = new Note(buttonList.get(16), soundBank.e3, soundPool, "e3");
        f3 = new Note(buttonList.get(17), soundBank.f3, soundPool, "f3");
        fSharp3 = new Note(buttonList.get(18), soundBank.fSharp3, soundPool, "fSharp3");
        g3 = new Note(buttonList.get(19), soundBank.g3, soundPool, "g3");
        gSharp3 = new Note(buttonList.get(20), soundBank.gSharp3, soundPool, "gSharp3");
        a3 = new Note(buttonList.get(21), soundBank.a3, soundPool, "a3");
        aSharp3 = new Note(buttonList.get(22), soundBank.aSharp3, soundPool, "aSharp3");
        b3 = new Note(buttonList.get(23), soundBank.b3, soundPool, "b3");
        c4 = new Note(buttonList.get(24), soundBank.c4, soundPool, "c4");
        cSharp4 = new Note(buttonList.get(25), soundBank.cSharp4, soundPool, "cSharp4");
        d4 = new Note(buttonList.get(26), soundBank.d4, soundPool, "d4");
        dSharp4 = new Note(buttonList.get(27), soundBank.dSharp4, soundPool, "dSharp4");
        e4 = new Note(buttonList.get(28), soundBank.e4, soundPool, "e4");
        f4 = new Note(buttonList.get(29), soundBank.f4, soundPool, "f4");
        fSharp4 = new Note(buttonList.get(30), soundBank.fSharp4, soundPool, "fSharp4");
        g4 = new Note(buttonList.get(31), soundBank.g4, soundPool, "g4");
        gSharp4 = new Note(buttonList.get(32), soundBank.gSharp4, soundPool, "gSharp4");
        a4 = new Note(buttonList.get(33), soundBank.a4, soundPool, "a4");
        aSharp4 = new Note(buttonList.get(34), soundBank.aSharp4, soundPool, "aSharp4");
        b4 = new Note(buttonList.get(35), soundBank.b4, soundPool, "b4");
        c5 = new Note(buttonList.get(36), soundBank.c5, soundPool, "c5");

        noteList = new ArrayList<>(Arrays.asList(c2, cSharp2, d2, dSharp2, e2, f2, fSharp2, g2, gSharp2, a2, aSharp2, b2, c3, cSharp3, d3, dSharp3, e3, f3, fSharp3, g3, gSharp3, a3, aSharp3, b3, c4, cSharp4, d4, dSharp4, e4, f4, fSharp4, g4, gSharp4, a4, aSharp4, b4, c5));
    }


    public Note getNote (String id)
    {
        for (int i = 0; i < noteList.size(); i++)
        {
            if (noteList.get(i).getNoteName().equals(id))
            {
                return noteList.get(i);
            }
        }
        Log.d(TAG, " Error: Note not found");
        return null;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if (soundBank.loaded())
        {
            // select correct note to play based on given view Id
            String tempId = v.getResources().getResourceName(v.getId()).substring(21);
            Note currentNote = getNote(tempId);
            if (currentNote!= null)
            {
                motionHandler(event, currentNote);
            }
            else
            {
                Log.d(TAG, "Error: no sample loaded!");
            }
        }
        return true;
    }




    public boolean motionHandler(MotionEvent event, Note note)
    {

        float volume = 0;

        switch (event.getAction())
        {
            // start playing when touch is held down
            case MotionEvent.ACTION_DOWN:
            {
                Log.d(TAG, "Now playing" + note.getNoteName());
                playNote(note, event, volume);
                break;
            }

            // apply aftertouch effects during playing
            case MotionEvent.ACTION_MOVE:
            {
                AfterTouchHandler(event, note);
                break;
            }

            // stop playing when touch is released
            case MotionEvent.ACTION_UP:
            {
                stopNote(note);
                break;
            }
        }
        return true;
    }




    public void playNote(Note note, MotionEvent event, float volume)
    {
        // show visual pressure feedback
        note.getTrigger().setPressed(true);

        // only play note if ADSR envelope allows it
        if (attackTime + decayTime + sustain + releaseTime !=0)
        {
            note.setEnvelope(attackTime, decayTime, sustain, releaseTime);
            // uses yCoordinates of touch to determine note velocity
            volume = volumeConverter(event.getY());


            // prevent conflicts if the same note is already playing
            if (note.getPhase().equals("Release"))
            {
                Log.d(TAG, "still playing!");
                note.interrupt();
                note.stop();
            }

            // play note but only use attack fade-in if variable is significant
            if (note.getA() > MINIMUM_VALUE)
            {
                note.play(0);
                note.attack(volume);
            }
            else if (note.getD() > MINIMUM_VALUE)
            {
                note.play(volume);
                note.decay();
            }
            else
            {
                note.play(volume);
            }
            if (vibrato)
            {
                lfo.startVibrato(note);
            }
        }
    }


    public void stopNote(Note note)
    {
        note.getTrigger().setPressed(false);
        if (vibrato)
        {
            lfo.stopVibrato(note);
        }

        // interrupt phases as necessary
        if (note.getPhase().equals("Attack"))
        {
            note.interruptAttack();
        }
        else if (note.getPhase().equals("Decay"))
        {
            note.interruptDecay();
        }

        // only use release fadeout if variable is significant
        if (note.getR() > 0)
        {
            note.release();
        }
        else
        {
            note.stop();
        }
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK)
            {
                retrieveFXSettings(data);
                if (sustain >= 1)
                {
                    sustain = 0.99f;
                }
                Log.d(TAG, "Sustain2 = " + sustain);

                preventVolumeConflicts();
                preventPitchConflicts();
            }
            if (resultCode == RESULT_CANCELED)
            {
                Log.d(TAG, "no result");
            }
        }
    }

    public void preventVolumeConflicts()
    {
        if ((attackTime > 0 || decayTime > 0) && aftSelect.equals("Volume"))
        {
            aftSelect = "Off";
        }
    }

    public void preventPitchConflicts()
    {
        if (vibrato && aftSelect.equals("Pitch"))
        {
            aftSelect = "Off";
        }
    }

    public void retrieveFXSettings (Intent data)
    {
        aftSelect = data.getStringExtra("afterTouch");
        modSelect = data.getStringExtra("modWheel");
        releaseTime = (int)(ENV_MULTIPLIER * logScale(data.getIntExtra("release", 0)));
        attackTime = (int)(ENV_MULTIPLIER * logScale(data.getIntExtra("attack", 0)));
        decayTime = (int)(ENV_MULTIPLIER * logScale(data.getIntExtra("decay", 0)));
        sustain = (data.getIntExtra("sustain", 0))/700;
        Log.d(TAG, "Sustain2 = " + sustain);
        vibrato = (data.getBooleanExtra("vibrato", false));
    }



    public void AfterTouchHandler(MotionEvent event, Note note)
    {

        if (aftSelect.equals("Pitch"))
        {
            float pitch = (float)((volumeConverter(event.getY()) -0.6)/20) + 1;
            note.setPitch(pitch);
        }
        else if (aftSelect.equals("Volume"))
        {
            float volume2 = volumeConverter(event.getY());
            note.setVolume(volume2);
        }
        else if (aftSelect.equals("Vibrato Intensity"))
        {
            float offset = (event.getY())/14000;
            Log.d(TAG,"offset = " + offset);
            float lowerBound = 1 - offset;
            float upperBound = 1 + offset;
            lfo.setIntensity(lowerBound, upperBound);

        }
    }



    public void onSoundKill(View view)
    {
        soundBank.stopAllSounds();
    }


    public float volumeConverter(float yCoordinates)
    {
        return (yCoordinates + 200)/1000;
    }


    // account for variable note release timing
    public void releaseDelay(final Note note)
    {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run() {
                Log.d(TAG, "Note released!");
                note.stop();
            }

        }, releaseTime);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public void onStop()
    {
        soundBank.unloadAll();
        super.onStop();
    }*/
}
