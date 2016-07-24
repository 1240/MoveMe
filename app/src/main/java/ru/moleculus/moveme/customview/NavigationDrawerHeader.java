package ru.moleculus.moveme.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.data.beans.User;


/**
 * Created by Oleg on 02.03.2016.
 */
public class NavigationDrawerHeader extends LinearLayout{

    protected View headerView;

    public NavigationDrawerHeader(Context context) {
        super(context);
        init();
    }

    public NavigationDrawerHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NavigationDrawerHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        headerView = inflater.inflate(R.layout.drawer_header_layout, null);
        addView(headerView);
    }

}
