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

import java.util.HashMap;


public class MainActivity extends Activity implements View.OnTouchListener {

    private Button buttonC2, buttonCSharp2, buttonD2, buttonDSharp2, buttonE2, buttonF2, buttonFSharp2, buttonG2, buttonGSharp2, buttonA2, buttonASharp2, buttonB2, buttonC3, buttonCSharp3, buttonD3, buttonDSharp3, buttonE3, buttonF3, buttonFSharp3, buttonG3, buttonGSharp3, buttonA3, buttonASharp3, buttonB3, buttonC4;
    private boolean playing = false, pitchAft = false;
    private String aftSelect = "Volume";
    private int releaseTime = 0;
    private static final int SEEKBAR_MIN = 1, SEEKBAR_MAX = 5000;
    private static final double LOG_MIN = 0.0, LOG_MAX = 3.69897;
    public static String PACKAGE_NAME;
    private SoundBank soundBank;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PACKAGE_NAME = getApplicationContext().getPackageName();


        soundBank = new SoundBank(getApplicationContext());
        soundBank.setInstrument("square");
        soundBank.loadSounds(PACKAGE_NAME);

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

    public void onEffectsStart(View v)
    {

        Intent i = new Intent(this, FXActivity.class);
        startActivityForResult(i, 1);
    }


    private void register(int buttonResourceId)
    {
        findViewById(buttonResourceId).setOnTouchListener(this);
    }


    // assign buttons with onTouchListener
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

        if (soundBank.loaded())
        {

            switch(v.getId()){

                case R.id.C2:

                    buttonPress(event, buttonC2, soundBank.c2);
                    break;

                case R.id.CSharp2:

                    buttonPress(event, buttonCSharp2, soundBank.cSharp2);
                    break;

                case R.id.D2:

                    buttonPress(event, buttonD2, soundBank.d2);
                    break;

                case R.id.DSharp2:

                    buttonPress(event, buttonDSharp2, soundBank.dSharp2);
                    break;

                case R.id.E2:
                    buttonPress(event, buttonE2, soundBank.e2);
                    break;

                case R.id.F2:
                    buttonPress(event, buttonF2, soundBank.f2);
                    break;

                case R.id.FSharp2:
                    buttonPress(event, buttonFSharp2, soundBank.fSharp2);
                    break;

                case R.id.G2:
                    buttonPress(event, buttonG2, soundBank.g2);
                    break;

                case R.id.GSharp2:
                    buttonPress(event, buttonGSharp2, soundBank.gSharp2);
                    break;

                case R.id.A2:
                    buttonPress(event, buttonA2, soundBank.a2);
                    break;

                case R.id.ASharp2:
                    buttonPress(event, buttonASharp2, soundBank.aSharp2);
                    break;

                case R.id.B2:
                    buttonPress(event, buttonB2, soundBank.b2);
                    break;

                case R.id.C3:

                    buttonPress(event, buttonC3, soundBank.c3);
                    break;

                case R.id.CSharp3:
                    buttonPress(event, buttonCSharp3, soundBank.cSharp3);
                    break;

                case R.id.D3:
                    buttonPress(event, buttonD3, soundBank.d3);
                    break;

                case R.id.DSharp3:
                    buttonPress(event, buttonDSharp3, soundBank.dSharp3);
                    break;

                case R.id.E3:
                    buttonPress(event, buttonE3, soundBank.e3);
                    break;

                case R.id.F3:
                    buttonPress(event, buttonF3, soundBank.f3);
                    break;

                case R.id.FSharp3:
                    buttonPress(event, buttonFSharp3, soundBank.fSharp3);
                    break;

                case R.id.G3:
                    buttonPress(event, buttonG3, soundBank.g3);
                    break;

                case R.id.GSharp3:
                    buttonPress(event, buttonGSharp3, soundBank.gSharp3);
                    break;

                case R.id.A3:
                    buttonPress(event, buttonA3, soundBank.a3);
                    break;

                case R.id.ASharp3:
                    buttonPress(event, buttonASharp3, soundBank.aSharp3);
                    break;

                case R.id.B3:
                    buttonPress(event, buttonB3, soundBank.b3);
                    break;

                case R.id.C4:
                    buttonPress(event, buttonC4, soundBank.c4);
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
                //int streamId = soundPool.play(soundId, volume, volume, 1, -1, 1f);
                int streamId = soundBank.playSound(soundId, volume);
                // associate button with current sound
                soundBank.mapStream(button, streamId);
                Log.d("1", "ID ="+ streamId);
                button.setPressed(true);
                playing = true;

                break;

            case MotionEvent.ACTION_MOVE:
            {
                AfterTouchHandler(event, button);
                break;
            }

            // stop playing when touch is released
            case MotionEvent.ACTION_UP:
                button.setPressed(false);
                if (releaseTime > 10)
                {
                    soundBank.fadeOut(soundBank.getStream(button), 0.7f, releaseTime);

                }
                releaseDelay(soundBank.getStream(button));

                playing = false;
                break;
        }
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                aftSelect = data.getStringExtra("afterTouch");
            }
            if (resultCode == RESULT_CANCELED) {
                Log.d("1", "no result");
            }
        }
    }



    public void AfterTouchHandler(MotionEvent event, Button button)
    {
        switch (aftSelect)
        {
            case "Pitch":
            {
                float pitch = (float)(volumeConverter(event.getY()) -0.6) /20 + 1;
                Log.d("1", "Pitch = " + pitch);
                soundBank.setPitch(soundBank.getStream(button), pitch);

            }
            case "Volume":
            {
                float volume2 = volumeConverter(event.getY());
                soundBank.setVolume(soundBank.getStream(button), volume2);
            }
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
    public void releaseDelay(final int streamId)
    {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run() {
                Log.d("1", "Note released!");
                soundBank.stopSound(streamId);
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
