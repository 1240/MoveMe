package ru.moleculus.moveme.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.noisyz.databindinglibrary.annotations.field.ImageField;
import com.noisyz.databindinglibrary.bind.base.impl.ObjectDataBinder;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.customview.MoveMeGalleryImageProvider;

/**
 * Created by Oleg on 23.03.2016.
 */
public class ShowImageActivity extends BaseActivity {

    @ImageField(imageProvider = MoveMeGalleryImageProvider.class)
    private String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_image_activity);
        initToolbar();
        url = getIntent().getExtras().getString(EXTRA_IMAGE);
        new ObjectDataBinder<ShowImageActivity>(this).registerView(this).bindUI();
    }

    private void initToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
