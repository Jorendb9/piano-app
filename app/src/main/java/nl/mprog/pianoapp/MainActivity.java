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
    public static String PACKAGE_NAME;
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
                    Log.d("1", "Attack Time (ms) = " + attackTime);
                }
                else if (modSelect.equals("Release"))
                {
                    releaseTime = (int) (1000 * logScale(temp));
                    Log.d("1", "Release Time (ms) = " + releaseTime);
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



        buttonC2 = (Button) findViewById(R.id.C2);
        buttonCSharp2 = (Button) findViewById(R.id.CSharp2);
        buttonD2 = (Button) findViewById(R.id.D2);
        buttonDSharp2 = (Button) findViewById(R.id.DSharp2);
        buttonE2 = (Button) findViewById(R.id.E2);
        buttonF2 = (Button) findViewById(R.id.F2);
        buttonFSharp2 = (Button) findViewById(R.id.FSharp2);
        buttonG2 = (Button) findViewById(R.id.G2);
        buttonGSharp2 = (Button) findViewById(R.id.GSharp2);
        buttonA2 = (Button) findViewById(R.id.A2);
        buttonASharp2 = (Button) findViewById(R.id.ASharp2);
        buttonB2 = (Button) findViewById(R.id.B2);
        buttonC3 = (Button) findViewById(R.id.C3);
        buttonCSharp3 = (Button) findViewById(R.id.CSharp3);
        buttonD3 = (Button) findViewById(R.id.D3);
        buttonDSharp3 = (Button) findViewById(R.id.DSharp3);
        buttonE3 = (Button) findViewById(R.id.E3);
        buttonF3 = (Button) findViewById(R.id.F3);
        buttonFSharp3 = (Button) findViewById(R.id.FSharp3);
        buttonG3 = (Button) findViewById(R.id.G3);
        buttonGSharp3 = (Button) findViewById(R.id.GSharp3);
        buttonA3 = (Button) findViewById(R.id.A3);
        buttonASharp3 = (Button) findViewById(R.id.ASharp3);
        buttonB3 = (Button) findViewById(R.id.B3);
        buttonC4 = (Button) findViewById(R.id.C4);


        buttonList = new ArrayList<>(Arrays.asList(buttonC2, buttonCSharp2, buttonD2, buttonDSharp2, buttonE2,
        buttonF2, buttonFSharp2, buttonG2, buttonGSharp2, buttonA2, buttonASharp2, buttonB2, buttonC3, buttonCSharp3, buttonD3,
        buttonDSharp3, buttonE3, buttonF3, buttonFSharp3, buttonG3, buttonGSharp3, buttonA3, buttonASharp3, buttonB3, buttonC4));


        register(R.id.C2);
        register(R.id.CSharp2);
        register(R.id.D2);
        register(R.id.DSharp2);
        register(R.id.E2);
        register(R.id.F2);
        register(R.id.FSharp2);
        register(R.id.G2);
        register(R.id.GSharp2);
        register(R.id.A2);
        register(R.id.ASharp2);
        register(R.id.B2);

        register(R.id.C3);
        register(R.id.CSharp3);
        register(R.id.D3);
        register(R.id.DSharp3);
        register(R.id.E3);
        register(R.id.F3);
        register(R.id.FSharp3);
        register(R.id.G3);
        register(R.id.GSharp3);
        register(R.id.A3);
        register(R.id.ASharp3);
        register(R.id.B3);
        register(R.id.C4);

        Log.d("Main", "Note ID " + soundBank.idList.get(0));
        c2 = new Note(buttonList.get(0), soundBank.c2, soundPool);
        cSharp2 = new Note(buttonList.get(1), soundBank.cSharp2, soundPool);
        d2 = new Note(buttonList.get(2), soundBank.d2, soundPool);
        dSharp2 = new Note(buttonList.get(3), soundBank.dSharp2, soundPool);
        e2 = new Note(buttonList.get(4), soundBank.e2, soundPool);
        f2 = new Note(buttonList.get(5), soundBank.f2, soundPool);
        fSharp2 = new Note(buttonList.get(6), soundBank.fSharp2, soundPool);
        g2 = new Note(buttonList.get(7), soundBank.g2, soundPool);
        gSharp2 = new Note(buttonList.get(8), soundBank.gSharp2, soundPool);
        a2 = new Note(buttonList.get(9), soundBank.a2, soundPool);
        aSharp2 = new Note(buttonList.get(10), soundBank.aSharp2, soundPool);
        b2 = new Note(buttonList.get(11), soundBank.b2, soundPool);
        c3 = new Note(buttonList.get(12), soundBank.c3, soundPool);
        cSharp3 = new Note(buttonList.get(13), soundBank.cSharp3, soundPool);
        d3 = new Note(buttonList.get(14), soundBank.d3, soundPool);
        dSharp3 = new Note(buttonList.get(15), soundBank.dSharp3, soundPool);
        e3 = new Note(buttonList.get(16), soundBank.e3, soundPool);
        f3 = new Note(buttonList.get(17), soundBank.f3, soundPool);
        fSharp3 = new Note(buttonList.get(18), soundBank.fSharp3, soundPool);
        g3 = new Note(buttonList.get(19), soundBank.g3, soundPool);
        gSharp3 = new Note(buttonList.get(20), soundBank.gSharp3, soundPool);
        a3 = new Note(buttonList.get(21), soundBank.a3, soundPool);
        aSharp3 = new Note(buttonList.get(22), soundBank.aSharp3, soundPool);
        b3 = new Note(buttonList.get(23), soundBank.b3, soundPool);
        c4 = new Note(buttonList.get(24), soundBank.c4, soundPool);

        noteList = new ArrayList<>(Arrays.asList(c2, cSharp2, d2, dSharp2, e2, f2, fSharp2, g2, gSharp2, a2, aSharp2, b2, c3, cSharp3, d3, dSharp3, e3, f3, fSharp3, g3, gSharp3, a3, aSharp3, b3, c4));
    }




    @Override
    public boolean onTouch(View v, MotionEvent event)
    {

        if (soundBank.loaded())
        {

            switch(v.getId()){

                case R.id.C2:

                    buttonPress(event, c2);
                    break;

                case R.id.CSharp2:

                    buttonPress(event, cSharp2);
                    break;

                case R.id.D2:

                    buttonPress(event, d2);
                    break;

                case R.id.DSharp2:

                    buttonPress(event, dSharp2);
                    break;

                case R.id.E2:
                    buttonPress(event, e2);
                    break;

                case R.id.F2:
                    buttonPress(event, f2);
                    break;

                case R.id.FSharp2:
                    buttonPress(event, fSharp2);
                    break;

                case R.id.G2:
                    buttonPress(event,g2);
                    break;

                case R.id.GSharp2:
                    buttonPress(event, gSharp2);
                    break;

                case R.id.A2:
                    buttonPress(event, a2);
                    break;

                case R.id.ASharp2:
                    buttonPress(event, aSharp2);
                    break;

                case R.id.B2:
                    buttonPress(event, b2);
                    break;

                case R.id.C3:

                    buttonPress(event, c3);
                    break;

                case R.id.CSharp3:
                    buttonPress(event,cSharp3);
                    break;

                case R.id.D3:
                    buttonPress(event, d3);
                    break;

                case R.id.DSharp3:
                    buttonPress(event, dSharp3);
                    break;

                case R.id.E3:
                    buttonPress(event, e3);
                    break;

                case R.id.F3:
                    buttonPress(event, f3);
                    break;

                case R.id.FSharp3:
                    buttonPress(event, fSharp3);
                    break;

                case R.id.G3:
                    buttonPress(event, g3);
                    break;

                case R.id.GSharp3:
                    buttonPress(event, gSharp3);
                    break;

                case R.id.A3:
                    buttonPress(event, a3);
                    break;

                case R.id.ASharp3:
                    buttonPress(event, aSharp3);
                    break;

                case R.id.B3:
                    buttonPress(event, b3);
                    break;

                case R.id.C4:
                    buttonPress(event, c4);
                    break;
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
                //note.release();
                releaseDelay(note);
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
