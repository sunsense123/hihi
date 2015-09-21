package com.unist.npc.queuing;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by jeonghyun on 2015. 9. 6..
 */
public class CustomscrollViewformap extends ScrollView {
    public CustomscrollViewformap(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public CustomscrollViewformap(Context context){
        super(context);
    }
    @Override
    public void computeScroll()
    {
        super.computeScroll();
        requestLayout();
    }
}
