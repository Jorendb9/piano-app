package nl.mprog.pianoapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;


public class FXActivity extends ActionBarActivity {

    private String afterTouch, modWheel;
    private Spinner spinnerAft, spinnerMod;
    private int release = 0, attack = 0, decay = 0;
    private float sustain = 700;
    private final static int TIME_MAX = 5000, VOLUME_MAX = 700;
    VerticalSeekBar attackBar, decayBar, sustainBar, releaseBar;
    private boolean vibrato = false;
    ToggleButton vButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fx);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            afterTouch = extras.getString("aftSelect");
            modWheel = extras.getString("modSelect");
            vibrato = extras.getBoolean("vibrato");
        }
        vButton = (ToggleButton) findViewById(R.id.toggleButton);
        vButton.setChecked(vibrato);


        initSpinners();
        initSeekBars();

    }

    public void initSpinners()
    {
        spinnerMod=(Spinner) findViewById(R.id.spinnerMod);
        spinnerAft=(Spinner) findViewById(R.id.spinnerAft);
        // set spinners initial setting to whatever value was selected before
        spinnerMod.setSelection(getIndex(spinnerMod, modWheel));
        spinnerAft.setSelection(getIndex(spinnerAft, afterTouch));
    }


    private int getIndex(Spinner spinner, String selection)
    {
        int index = 0;
        for (int i = 0; i<spinner.getCount();i++)
        {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(selection))
            {
                index = i;
                break;
            }

        }
        return index;
    }

    public void initSeekBars()
    {
        attackBar = (VerticalSeekBar) findViewById(R.id.attackBar);
        decayBar = (VerticalSeekBar) findViewById(R.id.decayBar);
        sustainBar = (VerticalSeekBar) findViewById(R.id.sustainBar);
        releaseBar = (VerticalSeekBar) findViewById(R.id.releaseBar);

        attackBar.setMax(TIME_MAX);
        decayBar.setMax(TIME_MAX);
        sustainBar.setMax(VOLUME_MAX);
        releaseBar.setMax(TIME_MAX);
        sustainBar.setProgress(sustainBar.getMax());
        seekBarSetup(attackBar);
        seekBarSetup(decayBar);
        seekBarSetup(sustainBar);
        seekBarSetup(releaseBar);

    }

    public void seekBarSetup(VerticalSeekBar seekBar)
    {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {

                // handle results from all 4 bars
                if (seekBar == attackBar)
                {
                    attack = progress;
                }
                if (seekBar == decayBar)
                {
                    decay = progress;
                }
                if (seekBar == sustainBar)
                {
                    sustain = progress;
                }
                if (seekBar == releaseBar)
                {
                    release = progress;
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {}
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
        returnFXSettings();
        finish();
    }


    public void returnFXSettings()
    {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("attack", attack);
        returnIntent.putExtra("decay", decay);
        returnIntent.putExtra("sustain", sustain);
        returnIntent.putExtra("release", release);
        returnIntent.putExtra("afterTouch",afterTouch);
        returnIntent.putExtra("modWheel",modWheel);
        returnIntent.putExtra("vibrato", vibrato);
        setResult(RESULT_OK,returnIntent);

    }

    public void onToggleClicked(View view)
    {
        // toggle is linked to vibrato variable
        vibrato = ((ToggleButton) view).isChecked();
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
