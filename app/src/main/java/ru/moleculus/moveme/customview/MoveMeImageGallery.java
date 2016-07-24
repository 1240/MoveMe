package ru.moleculus.moveme.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.noisyz.databindinglibrary.annotations.field.ImageField;
import com.noisyz.databindinglibrary.bind.base.impl.adapter.ConvertBindAdapter;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.widget.HListView;
import ru.moleculus.moveme.R;

/**
 * Created by Oleg on 23.03.2016.
 */
public class MoveMeImageGallery extends HListView implements ConvertBindAdapter.OnItemClickListener<String>, View.OnClickListener {

    private OnItemClickListener onAddItemClickListener;
    private MoveMeGalleryAdapter adapter;
    private ArrayList<String> urls;

    public MoveMeImageGallery(Context context) {
        super(context);
        init();
    }

    public MoveMeImageGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MoveMeImageGallery(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setImageUrls(null);
        View footer = View.inflate(getContext(), R.layout.move_me_gallery_add_button, null);
        footer.setOnClickListener(this);
        addFooterView(footer);
        adapter = new MoveMeGalleryAdapter(getContext(), new ArrayList<String>());
        setAdapter(adapter);
    }

    public void setOnAddItemClickListener(OnItemClickListener listener) {
        this.onAddItemClickListener = listener;
    }

    public void setImageUrls(ArrayList<String> urls) {
        if (urls == null) {
            urls = new ArrayList<String>();
        }
        this.urls = urls;
        adapter = new MoveMeGalleryAdapter(getContext(), urls);
        adapter.setOnItemClickListener(this);
        setAdapter(adapter);
    }

    public MoveMeGalleryAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onItemClick(int position, String o) {
        if (onAddItemClickListener != null) {
            onAddItemClickListener.onClickShowImage(o);
        }
    }

    @Override
    public void onClick(View v) {
        onAddItemClickListener.onClickAddImage();
    }


    private class MoveMeGalleryAdapter extends ConvertBindAdapter<String> {

        @ImageField(imageProvider = MoveMeGalleryImageProvider.class)
        private String url;

        public MoveMeGalleryAdapter(Context context, List<String> itemList) {
            super(context, itemList, R.layout.move_me_gallery_add_button);
        }

        @Override
        protected void updateFieldsByObject(int position, String o) {
            this.url = o;
        }
    }

    public interface OnItemClickListener {

        void onClickAddImage();

        void onClickShowImage(String url);
    }
}
