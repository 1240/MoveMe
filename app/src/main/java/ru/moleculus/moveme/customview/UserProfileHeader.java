package ru.moleculus.moveme.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import ru.moleculus.moveme.R;


/**
 * Created by Oleg on 04.03.2016.
 */
public class UserProfileHeader extends NavigationDrawerHeader{

    public UserProfileHeader(Context context) {
        super(context);
    }

    public UserProfileHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UserProfileHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        headerView = inflater.inflate(R.layout.profile_header_layout, null);
        addView(headerView);
    }
}
