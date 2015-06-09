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
import android.widget.TextView;
import android.widget.Toast;



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
    public boolean onTouch(View v, MotionEvent event) {

        float volume = setVolume();

        if (loaded)
        {
            switch(v.getId()){
                case R.id.C:

                    buttonPress(event, volume, buttonC, c);

                    break;

                case R.id.CSharp:
                    buttonPress(event, volume, buttonCSharp, cSharp);
                    break;

                case R.id.D:
                    buttonPress(event, volume, buttonD, d);
                    break;

                case R.id.DSharp:
                    buttonPress(event, volume, buttonDSharp, dSharp);
                    break;

                case R.id.E:
                    buttonPress(event, volume, buttonE, e);
                    break;

                case R.id.F:
                    buttonPress(event, volume, buttonF, f);
                    break;

                case R.id.FSharp:
                    buttonPress(event, volume, buttonFSharp, fSharp);
                    break;

                case R.id.G:
                    buttonPress(event, volume, buttonG, g);
                    break;

                case R.id.GSharp:
                    buttonPress(event, volume, buttonGSharp, gSharp);
                    break;

                case R.id.A:
                    buttonPress(event, volume, buttonA, a);
                    break;

                case R.id.ASharp:
                    buttonPress(event, volume, buttonASharp, aSharp);
                    break;

                case R.id.B:
                    buttonPress(event, volume, buttonB, b);
                    break;

                case R.id.C8Va:
                    buttonPress(event, volume, buttonC8Va, c8va);
                    break;
            }
        }
        return true;
    }


    public boolean buttonPress(MotionEvent event, float volume, Button button, int soundId)
    {
        int currentPlay = 0;
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                currentPlay = soundPool.play(soundId, volume, volume, 1, 0, 1f);
                button.setPressed(true);
                return true;
            case MotionEvent.ACTION_UP:
                button.setPressed(false);
                soundPool.stop(currentPlay);
                return true;
        }
        return false;
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


    public float setVolume()
    {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        float actualVolume = (float) audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        return actualVolume / maxVolume;
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        // MotionEvent object holds X-Y values
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            String text = "You click at x = " + event.getX() + " and y = " + event.getY();
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }

        return super.onTouchEvent(event);
    }*/




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
