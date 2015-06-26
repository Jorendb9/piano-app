package nl.mprog.pianoapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;


import java.util.ArrayList;
import java.util.Arrays;



public class SoundBank {

    private String instrument;
    private SoundPool soundPool;
    private Context ctx;
    private Boolean loaded = false;
    public ArrayList<Integer> idList;

    public int c2, cSharp2, d2, dSharp2, e2, f2, fSharp2, g2, gSharp2, a2, aSharp2, b2, c3, cSharp3, d3, dSharp3, e3, f3, fSharp3, g3, gSharp3, a3, aSharp3, b3, c4, cSharp4, d4, dSharp4, e4, f4, fSharp4, g4, gSharp4, a4, aSharp4, b4, c5;

    public SoundBank(Context context)
    {
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

        idList = new ArrayList<>(Arrays.asList(c2, cSharp2, d2, dSharp2, e2, f2, fSharp2, g2,
        gSharp2, a2, aSharp2, b2, c3, cSharp3, d3, dSharp3, e3, f3, fSharp3, g3, gSharp3, a3, aSharp3, b3, c4, cSharp4, d4, dSharp4, e4, f4, fSharp4, g4, gSharp4, a4, aSharp4, b4, c5));

    }

    public void setInstrument(String selected)
    {
        instrument = selected;
    }

    public ArrayList<Integer> getIdList()
    {
        return idList;
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
        resId = getResId(instrument, "csharp4", packageName);
        cSharp4 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "d4", packageName);
        d4 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "dsharp4", packageName);
        dSharp4 = soundPool.load(ctx,resId, 1);
        resId = getResId(instrument, "e4", packageName);
        e4 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "f4", packageName);
        f4 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "fsharp4", packageName);
        fSharp4 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "g4", packageName);
        g4 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "gsharp4", packageName);
        gSharp4 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "a4", packageName);
        a4 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "asharp4", packageName);
        aSharp4 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "b4", packageName);
        b4 = soundPool.load(ctx, resId, 1);
        resId = getResId(instrument, "c5", packageName);
        c5 = soundPool.load(ctx, resId, 1);

    }

    // use instrument string name to find the proper audio sample resource
    public int getResId(String instrument, String note, String packageName)
    {
        String tempString = instrument + note;
        return ctx.getResources().getIdentifier(tempString, "raw", packageName);
    }

    public SoundPool getSoundPool()
    {
        return soundPool;
    }

    public void stopAllSounds()
    {
        soundPool.autoPause();
    }

    public boolean loaded()
    {
        return loaded;
    }

}
