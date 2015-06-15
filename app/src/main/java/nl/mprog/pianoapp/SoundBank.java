package nl.mprog.pianoapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class SoundBank {

    private String instrument;
    private SoundPool soundPool;
    private Context ctx;
    private Boolean loaded = false;
    private HashMap<Button, Integer> soundMap = new HashMap<>();

    public int c2, cSharp2, d2, dSharp2, e2, f2, fSharp2, g2, gSharp2, a2, aSharp2, b2, c3, cSharp3, d3, dSharp3, e3, f3, fSharp3, g3, gSharp3, a3, aSharp3, b3, c4;

    public SoundBank(Context context) {
        ctx = context;
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener()
        {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status)
            {
                loaded = true;
            }
        });


    }

    public void setInstrument(String selected)
    {
        instrument = selected;
    }



    public void loadSounds(String packageName)
    {
        int resId;
        resId = getResId(instrument, "c2", packageName);
        c2 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "csharp2", packageName);
        cSharp2 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "d2", packageName);
        d2 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "dsharp2", packageName);
        dSharp2 = soundPool.load(ctx,resId, 1);
        resId = getResId(instrument, "e2", packageName);
        e2 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "f2", packageName);
        f2 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "fsharp2", packageName);
        fSharp2 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "g2", packageName);
        g2 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "gsharp2", packageName);
        gSharp2 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "a2", packageName);
        a2 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "asharp2", packageName);
        aSharp2 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "b2", packageName);
        b2 = soundPool.load(ctx, resId, 1);

        resId = getResId(instrument, "c3", packageName);
        c3 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "csharp3", packageName);
        cSharp3 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "d3", packageName);
        d3 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "dsharp3", packageName);
        dSharp3 = soundPool.load(ctx,resId, 1);
        resId = getResId(instrument, "e3", packageName);
        e3 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "f3", packageName);
        f3 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "fsharp3", packageName);
        fSharp3 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "g3", packageName);
        g3 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "gsharp3", packageName);
        gSharp3 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "a3", packageName);
        a3 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "asharp3", packageName);
        aSharp3 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "b3", packageName);
        b3 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "c4", packageName);
        c4 = soundPool.load(ctx, resId, 1);

    }

    public int getResId(String instrument, String note, String packageName)
    {
        String tempString = instrument + note;
        return ctx.getResources().getIdentifier(tempString, "raw", packageName);
    }



    public void stopAllSounds()
    {
        soundPool.autoPause();
        for (int value : soundMap.values())
        {
            soundPool.stop(value);
        }
    }

    public int playSound(int soundId, float volume)
    {
        return soundPool.play(soundId, volume, volume, 1, -1, 1f);
    }

    public void stopSound(int streamId)
    {
        soundPool.stop(streamId);
    }


    public void mapStream(Button button, int streamId)
    {
        soundMap.put(button, streamId);
    }

    public int getStream(Button button)
    {
        return soundMap.get(button);
    }

    public void setPitch(int streamId, float pitch)
    {
        soundPool.setRate(streamId, pitch);
    }

    public void setVolume(int streamId, float volume)
    {
        soundPool.setVolume(streamId, volume, volume);
    }

    public boolean loaded()
    {
        return loaded;
    }

    public void fadeOut(final int streamId, final float initVolume, int releaseTime)
    {
        final int steps = 100;
        final long timeStep = (long)releaseTime/100;
        final float volumeStep = initVolume/100;


        /*for (int i = 0; i <= steps; i++)
        {
            soundPool.setVolume(streamId, initVolume, initVolume);
            initVolume =- volumeStep;
            delay(timeStep);
        }
        soundPool.stop(streamId);*/

        for (int i = 0; i <= steps; i++)
        {
            final int j = i;
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {

                    float tempVolume = initVolume - j*volumeStep;
                    soundPool.setVolume(streamId, tempVolume, tempVolume);

                }
            }, timeStep);
        }
        soundPool.stop(streamId);
    }


    public void delay (final long time)
    {
        /*Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {

            }
        }, time);*/

    }





    public void unloadAll()
    {
        soundPool.release();
    }
}
