package nl.mprog.pianoapp;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;


public class MainActivity extends Activity implements View.OnTouchListener {

    private SoundPool soundPool;
    private int c, cSharp, d, dSharp, e, f, fSharp, g, gSharp, a, aSharp, b, c8va;
    boolean loaded = false;
    private Button buttonC, buttonCSharp, buttonD, buttonDSharp, buttonE, buttonF, buttonFSharp, buttonG, buttonGSharp, buttonA, buttonASharp, buttonB, buttonC8Va;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        initButtons();

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status) {
                loaded = true;
            }
        });

        loadSounds();

    }


    private void register(int buttonResourceId){
        findViewById(buttonResourceId).setOnTouchListener(this);
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
        int currentPlay = 0;
        float volume;
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:

                // uses yCoordinates of touch to determine note velocity
                volume = volumeConverter(event.getY());
                currentPlay = soundPool.play(soundId, volume, volume, 1, 0, 1f);
                Log.d("1", "ID ="+ currentPlay);
                button.setPressed(true);
                break;

            // stop playing when touch is released
            case MotionEvent.ACTION_UP:
                button.setPressed(false);
                soundPool.stop(currentPlay);
                break;
        }
        return true;
    }


    public float volumeConverter(float yCoordinates)
    {
        return (yCoordinates + 200)/1000;
    }


    public void loadSounds()
    {
        c = soundPool.load(this, R.raw.guitarc3, 1);
        cSharp = soundPool.load(this, R.raw.guitarcsharp3, 1);
        d = soundPool.load(this, R.raw.guitard3, 1);
        dSharp = soundPool.load(this, R.raw.guitardsharp3, 1);
        e = soundPool.load(this, R.raw.guitare3, 1);
        f = soundPool.load(this, R.raw.guitarf3, 1);
        fSharp = soundPool.load(this, R.raw.guitarfsharp3, 1);
        g = soundPool.load(this, R.raw.guitarg3, 1);
        gSharp = soundPool.load(this, R.raw.guitargsharp3, 1);
        a = soundPool.load(this, R.raw.guitara3, 1);
        aSharp = soundPool.load(this, R.raw.guitarasharp3, 1);
        b = soundPool.load(this, R.raw.guitarb3, 1);
        c8va = soundPool.load(this, R.raw.guitarc4, 1);
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
