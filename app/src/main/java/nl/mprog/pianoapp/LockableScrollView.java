package nl.mprog.pianoapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;


public class LockableScrollView extends HorizontalScrollView {

    public LockableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }
    public LockableScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public LockableScrollView(Context context)
    {
        super(context);
    }


    // prevent scrollview from intercepting button presses
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        return false;
    }

}
