package nl.mprog.pianoapp;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
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
import android.widget.SeekBar;
import android.widget.ToggleButton;


public class MainActivity extends Activity implements View.OnTouchListener {

    private SoundPool soundPool;
    private int c2, cSharp2, d2, dSharp2, c, cSharp, d, dSharp, e, f, fSharp, g, gSharp, a, aSharp, b, c8va;
    boolean loaded = false;
    private Button buttonC2, buttonCSharp2, buttonD2, buttonDSharp2, buttonC, buttonCSharp, buttonD, buttonDSharp, buttonE, buttonF, buttonFSharp, buttonG, buttonGSharp, buttonA, buttonASharp, buttonB, buttonC8Va;
    private boolean loopable = true, pitchAft = false;
    private int currentPlay = 0, releaseTime = 0;
    private static final int SEEKBAR_MIN = 1, SEEKBAR_MAX = 5000;
    private static final double LOG_MIN = 0.0, LOG_MAX = 3.69897;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener()
        {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status) {
                loaded = true;
            }
        });

        loadSounds("guitar");
        initButtons();
        modWheelSetup();

    }

    private float logScale(float value) {
        //make a logarithmic scale based on value from seekbar
        return(float) (LOG_MIN+(value-SEEKBAR_MIN)*(LOG_MAX - LOG_MIN)/(SEEKBAR_MAX-SEEKBAR_MIN));
    }



    private void register(int buttonResourceId){
        findViewById(buttonResourceId).setOnTouchListener(this);
    }

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
                releaseTime = (int)(1000 * logScale(temp));
                Log.d("1", "Release Time (ms) = "+releaseTime);
            }
        });


    }

    // Simple Aftertouch Toggle controller
    public void onToggleClicked(View view) {
        boolean on = ((ToggleButton) view).isChecked();

        if (on)
        {
            pitchAft = true;
        } else {
            pitchAft = false;
        }
    }



    public void initButtons(){

        buttonC = (Button) findViewById(R.id.C3);
        buttonCSharp = (Button) findViewById(R.id.CSharp3);
        buttonD = (Button) findViewById(R.id.D3);
        buttonDSharp = (Button) findViewById(R.id.DSharp3);
        buttonE = (Button) findViewById(R.id.E3);
        buttonF = (Button) findViewById(R.id.F3);
        buttonFSharp = (Button) findViewById(R.id.FSharp3);
        buttonG = (Button) findViewById(R.id.G3);
        buttonGSharp = (Button) findViewById(R.id.GSharp3);
        buttonA = (Button) findViewById(R.id.A3);
        buttonASharp = (Button) findViewById(R.id.ASharp3);
        buttonB = (Button) findViewById(R.id.B3);
        buttonC8Va = (Button) findViewById(R.id.C4);

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
    }




    @Override
    public boolean onTouch(View v, MotionEvent event)
    {

        if (loaded)
        {
            switch(v.getId()){


                case R.id.C2:

                    buttonPress(event, buttonC2, c2);
                    break;

                case R.id.CSharp2:

                    buttonPress(event, buttonCSharp2, cSharp2);
                    break;

                case R.id.D2:

                    buttonPress(event, buttonD2, d2);
                    break;

                case R.id.DSharp2:

                    buttonPress(event, buttonDSharp2, dSharp2);
                    break;

                case R.id.C3:

                    buttonPress(event, buttonC, c);
                    break;

                case R.id.CSharp3:
                    buttonPress(event, buttonCSharp, cSharp);
                    break;

                case R.id.D3:
                    buttonPress(event, buttonD, d);
                    break;

                case R.id.DSharp3:
                    buttonPress(event, buttonDSharp, dSharp);
                    break;

                case R.id.E3:
                    buttonPress(event, buttonE, e);
                    break;

                case R.id.F3:
                    buttonPress(event, buttonF, f);
                    break;

                case R.id.FSharp3:
                    buttonPress(event, buttonFSharp, fSharp);
                    break;

                case R.id.G3:
                    buttonPress(event, buttonG, g);
                    break;

                case R.id.GSharp3:
                    buttonPress(event, buttonGSharp, gSharp);
                    break;

                case R.id.A3:
                    buttonPress(event, buttonA, a);
                    break;

                case R.id.ASharp3:
                    buttonPress(event, buttonASharp, aSharp);
                    break;

                case R.id.B3:
                    buttonPress(event, buttonB, b);
                    break;

                case R.id.C4:
                    buttonPress(event, buttonC8Va, c8va);
                    break;
            }
        }
        return true;
    }


    public boolean buttonPress(MotionEvent event, Button button, int soundId)
    {

        float volume;
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                // uses yCoordinates of touch to determine note velocity
                volume = volumeConverter(event.getY());
                currentPlay = soundPool.play(soundId, volume, volume, 1, -1, 1f);
                Log.d("1", "ID ="+ currentPlay);
                button.setPressed(true);
                break;

            case MotionEvent.ACTION_MOVE:
            {
                TouchHandler(event);
                break;
            }

            // stop playing when touch is released
            case MotionEvent.ACTION_UP:
                button.setPressed(false);
                releaseDelay(currentPlay);
                currentPlay = 0;

                break;
        }
        return true;
    }

    public void TouchHandler(MotionEvent event)
    {
        if (pitchAft)
        {
            float pitch = (float)(volumeConverter(event.getY()) -0.6) /10 + 1;
            Log.d("1", "Pitch = " + pitch);
            soundPool.setRate(currentPlay, pitch);
        }
        else
        {
            float volume2 = volumeConverter(event.getY());
            soundPool.setVolume(currentPlay, volume2, volume2);
        }
    }



    public float volumeConverter(float yCoordinates)
    {
        return (yCoordinates + 200)/1000;
    }


    public void loadSounds(String instrument)
    {
        c = soundPool.load(this, R.raw.squarec2, 1);
        cSharp = soundPool.load(this, R.raw.squarecsharp2, 1);
        d = soundPool.load(this, R.raw.squared2, 1);
        dSharp = soundPool.load(this, R.raw.squaredsharp2, 1);
        int resId;
        /*resId = getResId(instrument, "c3");
        c = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "csharp3");
        cSharp = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "d3");
        d = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "dsharp3");
        dSharp = soundPool.load(this,resId, 1);*/
        resId = getResId(instrument, "e3");
        e = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "f3");
        f = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "fsharp3");
        fSharp = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "g3");
        g = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "gsharp3");
        gSharp = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "a3");
        a = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "asharp3");
        aSharp = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "b3");
        b = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "c4");
        c8va = soundPool.load(this, resId, 1);
    }

    public int getResId(String instrument, String note)
    {
        String tempString = instrument + note;
        return getResources().getIdentifier(tempString, "raw", getPackageName());
    }





    // account for variable note release timing
    public void releaseDelay(final int streamID)
    {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run() {
                Log.d("1", "Note released!");
                soundPool.stop(streamID);
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
}
