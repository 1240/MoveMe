package com.noisyz.databindinglibrary.bind.base.impl.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.noisyz.databindinglibrary.bind.base.impl.ObjectDataBinder;

import java.util.List;

/**
 * Created by Oleg on 23.03.2016.
 */
public abstract class ConvertBindAdapter<O extends Object> extends BindAdapter<O> {

    private ObjectDataBinder<ConvertBindAdapter> adapterDataBinder;

    public ConvertBindAdapter(Context context, List<O> itemList, int layoutResID) {
        super(context, itemList, layoutResID);
        initAdapterBinder();
    }

    public ConvertBindAdapter(Context context, O[] os, int layoutResID) {
        super(context, os, layoutResID);
        initAdapterBinder();
    }

    private void initAdapterBinder() {
        adapterDataBinder = new ObjectDataBinder<ConvertBindAdapter>(this);
        getBindingManager().newBinder("adapter", adapterDataBinder);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = super.getView(position, convertView, parent);
        updateFieldsByObject(position, getItem(position));
        adapterDataBinder.registerView(convertView);
        adapterDataBinder.bindUI();
        return convertView;
    }


    @Override
    public ConvertBindAdapter<O> setOnItemClickListener(OnItemClickListener listener){
        return (ConvertBindAdapter<O>) super.setOnItemClickListener(listener);
    }

    protected abstract void updateFieldsByObject(int position, O o);
}
