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
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import java.util.HashMap;


public class MainActivity extends Activity implements View.OnTouchListener {

    private SoundPool soundPool;
    private int c2, cSharp2, d2, dSharp2, e2, f2, fSharp2, g2, gSharp2, a2, aSharp2, b2, c3, cSharp3, d3, dSharp3, e3, f3, fSharp3, g3, gSharp3, a3, aSharp3, b3, c4;
    private boolean loaded = false;
    private Button buttonC2, buttonCSharp2, buttonD2, buttonDSharp2, buttonE2, buttonF2, buttonFSharp2, buttonG2, buttonGSharp2, buttonA2, buttonASharp2, buttonB2, buttonC3, buttonCSharp3, buttonD3, buttonDSharp3, buttonE3, buttonF3, buttonFSharp3, buttonG3, buttonGSharp3, buttonA3, buttonASharp3, buttonB3, buttonC4;
    private boolean loopable = true, pitchAft = false;
    private int releaseTime = 0;
    private static final int SEEKBAR_MIN = 1, SEEKBAR_MAX = 5000;
    private static final double LOG_MIN = 0.0, LOG_MAX = 3.69897;
    private HashMap<Button, Integer> soundMap = new HashMap<>();





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

        loadSounds("square");
        initButtons();
        modWheelSetup();

    }

    private float logScale(float value)
    {
        //make a logarithmic scale based on value from seekbar
        return(float) (LOG_MIN+(value-SEEKBAR_MIN)*(LOG_MAX - LOG_MIN)/(SEEKBAR_MAX-SEEKBAR_MIN));
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

    // Aftertouch Toggle controller
    public void onToggleClicked(View view)
    {
        boolean on = ((ToggleButton) view).isChecked();

        if (on)
        {
            pitchAft = true;
        } else {
            pitchAft = false;
        }
    }


    private void register(int buttonResourceId)
    {
        findViewById(buttonResourceId).setOnTouchListener(this);
    }



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
    }




    @Override
    public boolean onTouch(View v, MotionEvent event)
    {

        if (loaded)
        {
            /*String IdAsString = v.getResources().getResourceName(v.getId());
            String noteName = IdAsString.substring(21);
            Log.d("1", "ID = "+noteName);
            String buttonName = "button" + noteName
            buttonPress(event, buttonC2, c2);*/

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

                case R.id.E2:
                    buttonPress(event, buttonE2, e2);
                    break;

                case R.id.F2:
                    buttonPress(event, buttonF2, f2);
                    break;

                case R.id.FSharp2:
                    buttonPress(event, buttonFSharp2, fSharp2);
                    break;

                case R.id.G2:
                    buttonPress(event, buttonG2, g2);
                    break;

                case R.id.GSharp2:
                    buttonPress(event, buttonGSharp2, gSharp2);
                    break;

                case R.id.A2:
                    buttonPress(event, buttonA2, a2);
                    break;

                case R.id.ASharp2:
                    buttonPress(event, buttonASharp2, aSharp2);
                    break;

                case R.id.B2:
                    buttonPress(event, buttonB2, b2);
                    break;

                case R.id.C3:

                    buttonPress(event, buttonC3, c3);
                    break;

                case R.id.CSharp3:
                    buttonPress(event, buttonCSharp3, cSharp3);
                    break;

                case R.id.D3:
                    buttonPress(event, buttonD3, d3);
                    break;

                case R.id.DSharp3:
                    buttonPress(event, buttonDSharp3, dSharp3);
                    break;

                case R.id.E3:
                    buttonPress(event, buttonE3, e3);
                    break;

                case R.id.F3:
                    buttonPress(event, buttonF3, f3);
                    break;

                case R.id.FSharp3:
                    buttonPress(event, buttonFSharp3, fSharp3);
                    break;

                case R.id.G3:
                    buttonPress(event, buttonG3, g3);
                    break;

                case R.id.GSharp3:
                    buttonPress(event, buttonGSharp3, gSharp3);
                    break;

                case R.id.A3:
                    buttonPress(event, buttonA3, a3);
                    break;

                case R.id.ASharp3:
                    buttonPress(event, buttonASharp3, aSharp3);
                    break;

                case R.id.B3:
                    buttonPress(event, buttonB3, b3);
                    break;

                case R.id.C4:
                    buttonPress(event, buttonC4, c4);
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
                //prevent scrolling while a key is being held down
                ((LockableScrollView)findViewById(R.id.horizontalScrollView)).setScrollingEnabled(false);


                // uses yCoordinates of touch to determine note velocity
                volume = volumeConverter(event.getY());
                int streamId = soundPool.play(soundId, volume, volume, 1, -1, 1f);
                // associate button with current sound
                soundMap.put(button, streamId);
                Log.d("1", "ID ="+ streamId);
                button.setPressed(true);

                break;

            case MotionEvent.ACTION_MOVE:
            {
                AfterTouchHandler(event, button);
                break;
            }

            // stop playing when touch is released
            case MotionEvent.ACTION_UP:
                button.setPressed(false);
                // allow scrolling after release again
                ((LockableScrollView)findViewById(R.id.horizontalScrollView)).setScrollingEnabled(true);
                releaseDelay(soundMap.get(button));
                break;
        }
        return true;
    }



    public void AfterTouchHandler(MotionEvent event, Button button)
    {
        if (pitchAft)
        {
            float pitch = (float)(volumeConverter(event.getY()) -0.6) /20 + 1;
            Log.d("1", "Pitch = " + pitch);
            soundPool.setRate(soundMap.get(button), pitch);
        }
        else
        {
            float volume2 = volumeConverter(event.getY());
            soundPool.setVolume(soundMap.get(button), volume2, volume2);
        }
    }


    // temporary solution to infinitely looping sounds
    public void onSoundKill(View view)
    {
        // pause all sounds currently playing and stop
        soundPool.autoPause();
        for (int value : soundMap.values())
        {
            soundPool.stop(value);
        }
    }


    public float volumeConverter(float yCoordinates)
    {
        return (yCoordinates + 200)/1000;
    }




    public void loadSounds(String instrument)
    {
        int resId;
        resId = getResId(instrument, "c2");
        c2 = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "csharp2");
        cSharp2 = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "d2");
        d2 = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "dsharp2");
        dSharp2 = soundPool.load(this,resId, 1);
        resId = getResId(instrument, "e2");
        e2 = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "f2");
        f2 = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "fsharp2");
        fSharp2 = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "g2");
        g2 = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "gsharp2");
        gSharp2 = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "a2");
        a2 = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "asharp2");
        aSharp2 = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "b2");
        b2 = soundPool.load(this, resId, 1);

        resId = getResId(instrument, "c3");
        c3 = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "csharp3");
        cSharp3 = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "d3");
        d3 = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "dsharp3");
        dSharp3 = soundPool.load(this,resId, 1);
        resId = getResId(instrument, "e3");
        e3 = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "f3");
        f3 = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "fsharp3");
        fSharp3 = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "g3");
        g3 = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "gsharp3");
        gSharp3 = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "a3");
        a3 = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "asharp3");
        aSharp3 = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "b3");
        b3 = soundPool.load(this, resId, 1);
        resId = getResId(instrument, "c4");
        c4 = soundPool.load(this, resId, 1);
    }

    public int getResId(String instrument, String note)
    {
        String tempString = instrument + note;
        return getResources().getIdentifier(tempString, "raw", getPackageName());
    }





    // account for variable note release timing
    public void releaseDelay(final int streamId)
    {

        // TODO Fade out over x amount of time
        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run() {
                Log.d("1", "Note released!");
                soundPool.stop(streamId);
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
