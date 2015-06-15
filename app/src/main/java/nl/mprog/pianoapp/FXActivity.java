package nl.mprog.pianoapp;

import android.media.audiofx.PresetReverb;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;


public class FXActivity extends ActionBarActivity {

    boolean reverbSwitch;
    PresetReverb reverb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fx);

        reverb = new PresetReverb(1,0);
        reverb.setPreset(PresetReverb.PRESET_LARGEROOM);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fx, menu);
        return true;
    }

    // Aftertouch Toggle controller
    public void onToggleClicked(View view)
    {
        reverbSwitch = ((ToggleButton) view).isChecked();
        reverb.setEnabled(true);
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
