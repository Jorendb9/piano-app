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

    private Button buttonC2, buttonCSharp2, buttonD2, buttonDSharp2, buttonE2, buttonF2, buttonFSharp2, buttonG2, buttonGSharp2, buttonA2, buttonASharp2, buttonB2, buttonC3, buttonCSharp3, buttonD3, buttonDSharp3, buttonE3, buttonF3, buttonFSharp3, buttonG3, buttonGSharp3, buttonA3, buttonASharp3, buttonB3, buttonC4;
    private boolean playing = false, pitchAft = false;
    private String aftSelect = "Volume", modSelect = "Release";
    private int releaseTime = 0, attackTime = 0;
    private static final int SEEKBAR_MIN = 1, SEEKBAR_MAX = 5000;
    private static final double LOG_MIN = 0.0, LOG_MAX = 3.69897;
    public static String PACKAGE_NAME, TAG = "Main";
    private ArrayList<Integer> noteIdList;
    private ArrayList<Note> noteList;
    private ArrayList<Button> buttonList;
    private SoundBank soundBank;
    private SoundPool soundPool;
    private Note c2, cSharp2, d2, dSharp2, e2, f2, fSharp2, g2, gSharp2, a2, aSharp2, b2, c3, cSharp3, d3, dSharp3, e3, f3, fSharp3, g3, gSharp3, a3, aSharp3, b3, c4;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PACKAGE_NAME = getApplicationContext().getPackageName();


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
        //make a logarithmic scale based on value from seekbar
        return(float) (LOG_MIN+(value-SEEKBAR_MIN)*(LOG_MAX - LOG_MIN)/(SEEKBAR_MAX-SEEKBAR_MIN));
    }



    //set up seekbar
    public void modWheelSetup()
    {
        SeekBar modWheel = (SeekBar)findViewById(R.id.seekBar);
        modWheel.setMax (SEEKBAR_MAX);

        modWheel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                float temp = seekBar.getProgress();
                if (modSelect.equals("Attack"))
                {
                    attackTime = (int) (1000 * logScale(temp));
                    Log.d(TAG, "Attack Time (ms) = " + attackTime);
                }
                else if (modSelect.equals("Release"))
                {
                    releaseTime = (int) (1000 * logScale(temp));
                    Log.d(TAG, "Release Time (ms) = " + releaseTime);
                }
            }
        });

    }


    // go to fx Activity
    public void onEffectsStart(View v)
    {

        Intent i = new Intent(this, FXActivity.class);
        startActivityForResult(i, 1);
    }


    private void register(int buttonResourceId)
    {
        findViewById(buttonResourceId).setOnTouchListener(this);
    }


    // assign buttons to onTouchListener
    public void initButtons(){



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


        buttonList = new ArrayList<>(Arrays.asList(buttonC2, buttonCSharp2, buttonD2, buttonDSharp2, buttonE2,
        buttonF2, buttonFSharp2, buttonG2, buttonGSharp2, buttonA2, buttonASharp2, buttonB2, buttonC3, buttonCSharp3, buttonD3,
        buttonDSharp3, buttonE3, buttonF3, buttonFSharp3, buttonG3, buttonGSharp3, buttonA3, buttonASharp3, buttonB3, buttonC4));


        register(R.id.c2);
        register(R.id.cSharp2);
        register(R.id.d2);
        register(R.id.dSharp2);
        register(R.id.e2);
        register(R.id.f2);
        register(R.id.fSharp2);
        register(R.id.g2);
        register(R.id.gSharp2);
        register(R.id.a2);
        register(R.id.aSharp2);
        register(R.id.b2);

        register(R.id.c3);
        register(R.id.cSharp3);
        register(R.id.d3);
        register(R.id.dSharp3);
        register(R.id.e3);
        register(R.id.f3);
        register(R.id.fSharp3);
        register(R.id.g3);
        register(R.id.gSharp3);
        register(R.id.a3);
        register(R.id.aSharp3);
        register(R.id.b3);
        register(R.id.c4);

        Log.d(TAG, "Note ID " + soundBank.idList.get(0));

    }

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

        noteList = new ArrayList<>(Arrays.asList(c2, cSharp2, d2, dSharp2, e2, f2, fSharp2, g2, gSharp2, a2, aSharp2, b2, c3, cSharp3, d3, dSharp3, e3, f3, fSharp3, g3, gSharp3, a3, aSharp3, b3, c4));
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
            String tempId = v.getResources().getResourceName(v.getId()).substring(21);
            Log.d(TAG, tempId);
            Note currentNote = getNote(tempId);
            if (currentNote!= null)
            {
                buttonPress(event, currentNote);
            }
            else
            {
                Log.d(TAG, "Error: no sample loaded!");
            }
        }
        return true;
    }


    public boolean buttonPress(MotionEvent event, Note note)
    {

        float volume;

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:

                note.setEnvelope(attackTime, 0, 0, releaseTime);
                // uses yCoordinates of touch to determine note velocity
                volume = volumeConverter(event.getY());
                note.getTrigger().setPressed(true);

                // prevent conflicts if the same note is already playing
                if (note.isPlaying())
                {
                    Log.d(TAG, "already playing!");
                    note.interrupt();
                    note.stop();
                }
                note.play(volume);

                break;

            case MotionEvent.ACTION_MOVE:
            {
                AfterTouchHandler(event, note);
                break;
            }

            // stop playing when touch is released
            case MotionEvent.ACTION_UP:
                note.getTrigger().setPressed(false);
                note.release();

                break;
        }
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                aftSelect = data.getStringExtra("afterTouch");
                modSelect = data.getStringExtra("modWheel");
                releaseTime = (int)(1000 * logScale(data.getIntExtra("release", 0)));
            }
            if (resultCode == RESULT_CANCELED) {
                Log.d("1", "no result");
            }
        }
    }



    public void AfterTouchHandler(MotionEvent event, Note note)
    {

        if (aftSelect.equals("Pitch"))
        {
            float pitch = (float)(volumeConverter(event.getY()) -0.6) /20 + 1;
            Log.d("1", "Pitch = " + pitch);
            note.setPitch(pitch);
        }
        else if (aftSelect.equals("Volume"))
        {
            float volume2 = volumeConverter(event.getY());
            note.setVolume(volume2);
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
                Log.d("1", "Note released!");
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
