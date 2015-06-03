package nl.mprog.pianoapp;

import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    private SoundPool soundPool;
    private int c3, cSharp3, d3, dSharp3, e3, f3, fSharp3, g3, gSharp3, a3, aSharp3, b3, c4;
    boolean loaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void loadSounds()
    {
        c3 = soundPool.load(this, R.raw.guitarc3, 1);
        cSharp3 = soundPool.load(this, R.raw.guitarcsharp3, 1);
        d3 = soundPool.load(this, R.raw.guitard3, 1);
        dSharp3 = soundPool.load(this, R.raw.guitardsharp3, 1);
        e3 = soundPool.load(this, R.raw.guitare3, 1);
        f3 = soundPool.load(this, R.raw.guitarf3, 1);
        fSharp3 = soundPool.load(this, R.raw.guitarfsharp3, 1);
        g3 = soundPool.load(this, R.raw.guitarg3, 1);
        gSharp3 = soundPool.load(this, R.raw.guitargsharp3, 1);
        a3 = soundPool.load(this, R.raw.guitara3, 1);
        aSharp3 = soundPool.load(this, R.raw.guitarasharp3, 1);
        b3 = soundPool.load(this, R.raw.guitarb3, 1);
        c4 = soundPool.load(this, R.raw.guitarc4, 1);
    }


    public void buttonOnClick(View view)
    {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        float actualVolume = (float) audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = actualVolume / maxVolume;

        if (loaded)
        {
            switch(view.getId())
            {
                case R.id.buttonC3:
                    soundPool.play(c3, volume, volume, 1, 0, 1f);
                    Log.e("Test", "Played sound");
                    break;

                case R.id.buttonCSharp3:
                    soundPool.play(cSharp3, volume, volume, 1, 0, 1f);
                    Log.e("Test", "Played sound");
                    break;

                case R.id.buttonD3:
                    soundPool.play(d3, volume, volume, 1, 0, 1f);
                    Log.e("Test", "Played sound");
                    break;

                case R.id.buttonDSharp3:
                    soundPool.play(dSharp3, volume, volume, 1, 0, 1f);
                    Log.e("Test", "Played sound");
                    break;

                case R.id.buttonE3:
                    soundPool.play(e3, volume, volume, 1, 0, 1f);
                    Log.e("Test", "Played sound");
                    break;

                case R.id.buttonF3:
                    soundPool.play(f3, volume, volume, 1, 0, 1f);
                    Log.e("Test", "Played sound");
                    break;

                case R.id.buttonFSharp3:
                    soundPool.play(fSharp3, volume, volume, 1, 0, 1f);
                    Log.e("Test", "Played sound");
                    break;

                case R.id.buttonG3:
                    soundPool.play(g3, volume, volume, 1, 0, 1f);
                    Log.e("Test", "Played sound");
                    break;

                case R.id.buttonGSharp3:
                    soundPool.play(gSharp3, volume, volume, 1, 0, 1f);
                    Log.e("Test", "Played sound");
                    break;

                case R.id.buttonA3:
                    soundPool.play(a3, volume, volume, 1, 0, 1f);
                    Log.e("Test", "Played sound");
                    break;

                case R.id.buttonASharp3:
                    soundPool.play(aSharp3, volume, volume, 1, 0, 1f);
                    Log.e("Test", "Played sound");
                    break;

                case R.id.buttonB3:
                    soundPool.play(b3, volume, volume, 1, 0, 1f);
                    Log.e("Test", "Played sound");
                    break;

                case R.id.buttonC4:
                    soundPool.play(c4, volume, volume, 1, 0, 1f);
                    Log.e("Test", "Played sound");
                    break;
            }
        }

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
