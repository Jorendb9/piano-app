package nl.mprog.pianoapp;

import android.content.Intent;
import android.media.audiofx.PresetReverb;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;


public class FXActivity extends ActionBarActivity {

    private String afterTouch, modWheel;
    private Spinner spinnerAft, spinnerMod;
    private int release;
    VerticalSeekBar attackBar, decayBar, sustainBar, releaseBar;
    TextView seekBarValue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fx);
        spinnerMod=(Spinner) findViewById(R.id.spinnerMod);
        spinnerAft=(Spinner) findViewById(R.id.spinnerAft);

        seekBarValue = (TextView) findViewById(R.id.seekBarValue);

        setupSeekBars();


    }

    public void setupSeekBars()
    {
        attackBar = (VerticalSeekBar) findViewById(R.id.attackBar);
        decayBar = (VerticalSeekBar) findViewById(R.id.decayBar);
        sustainBar = (VerticalSeekBar) findViewById(R.id.sustainBar);
        releaseBar = (VerticalSeekBar) findViewById(R.id.releaseBar);

        releaseBar.setMax(5000);

        releaseBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                seekBarValue.setText(String.valueOf(progress));
                release = progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fx, menu);
        return true;
    }

    public void onBack(View view)
    {
        finishActivity();
    }

    @Override
    public void onBackPressed()
    {
        finishActivity();
    }


    public void finishActivity()
    {
        afterTouch = spinnerAft.getSelectedItem().toString();
        modWheel = spinnerMod.getSelectedItem().toString();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("release", release);
        returnIntent.putExtra("afterTouch",afterTouch);
        returnIntent.putExtra("modWheel",modWheel);
        setResult(RESULT_OK,returnIntent);
        finish();
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
