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

        buttonC = (Button) findViewById(R.id.C);
        buttonCSharp = (Button) findViewById(R.id.CSharp);
        buttonD = (Button) findViewById(R.id.D);
        buttonDSharp = (Button) findViewById(R.id.DSharp);
        buttonE = (Button) findViewById(R.id.E);
        buttonF = (Button) findViewById(R.id.F);
        buttonFSharp = (Button) findViewById(R.id.FSharp);
        buttonG = (Button) findViewById(R.id.G);
        buttonGSharp = (Button) findViewById(R.id.GSharp);
        buttonA = (Button) findViewById(R.id.A);
        buttonASharp = (Button) findViewById(R.id.ASharp);
        buttonB = (Button) findViewById(R.id.B);
        buttonC8Va = (Button) findViewById(R.id.C8Va);

        register(R.id.C);
        register(R.id.CSharp);
        register(R.id.D);
        register(R.id.DSharp);
        register(R.id.E);
        register(R.id.F);
        register(R.id.FSharp);
        register(R.id.G);
        register(R.id.GSharp);
        register(R.id.A);
        register(R.id.ASharp);
        register(R.id.B);
        register(R.id.C8Va);
    }




    @Override
    public boolean onTouch(View v, MotionEvent event)
    {

        if (loaded)
        {
            switch(v.getId()){
                case R.id.C:

                    buttonPress(event, buttonC, c);
                    break;

                case R.id.CSharp:
                    buttonPress(event, buttonCSharp, cSharp);
                    break;

                case R.id.D:
                    buttonPress(event, buttonD, d);
                    break;

                case R.id.DSharp:
                    buttonPress(event, buttonDSharp, dSharp);
                    break;

                case R.id.E:
                    buttonPress(event, buttonE, e);
                    break;

                case R.id.F:
                    buttonPress(event, buttonF, f);
                    break;

                case R.id.FSharp:
                    buttonPress(event, buttonFSharp, fSharp);
                    break;

                case R.id.G:
                    buttonPress(event, buttonG, g);
                    break;

                case R.id.GSharp:
                    buttonPress(event, buttonGSharp, gSharp);
                    break;

                case R.id.A:
                    buttonPress(event, buttonA, a);
                    break;

                case R.id.ASharp:
                    buttonPress(event, buttonASharp, aSharp);
                    break;

                case R.id.B:
                    buttonPress(event, buttonB, b);
                    break;

                case R.id.C8Va:
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
                button.setPressed(true);
                return true;

            // stop playing when touch is released
            case MotionEvent.ACTION_UP:
                button.setPressed(false);
                soundPool.stop(currentPlay);
                return true;
        }
        return false;
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
