package com.noisyz.databindinglibrary.bind.base.impl.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.noisyz.databindinglibrary.bind.BindingManager;
import com.noisyz.databindinglibrary.bind.base.impl.ObjectDataBinder;
import com.noisyz.databindinglibrary.bind.base.UIBinder;
import com.noisyz.databindinglibrary.callback.DataUpdatedCallback;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Oleg on 23.03.2016.
 */
public class BindAdapter<O extends Object> extends BaseAdapter implements UIBinder {

    private static final int MODE_LIST = 0;
    private static final int MODE_ARRAY = 1;

    private O[] os;
    private List<O> itemList;
    private LayoutInflater inflater;
    private int layoutResID, mode;
    private Context context;
    private OnItemClickListener<O> onItemClickListener;
    private BindingManager bindingManager;
    private int creatingBindersCount = 0;

    public BindAdapter(Context context, List<O> itemList, int layoutResID) {
        this(context, layoutResID);
        if (itemList == null) {
            itemList = new ArrayList<O>();
        }
        this.itemList = itemList;
        mode = MODE_LIST;
        updateDataSet();
    }

    public BindAdapter(Context context, O[] os, int layoutResID) {
        this(context, layoutResID);
        this.os = os;
        mode = MODE_ARRAY;
        updateDataSet();
    }

    private BindAdapter(Context context, int layoutResID) {
        this.inflater = LayoutInflater.from(context);
        this.layoutResID = layoutResID;
        this.context = context;
        bindingManager = BindingManager.newInstance();
    }

    @Override
    public BindAdapter setDataUpdatedCallback(DataUpdatedCallback callback) {
        bindingManager.setDataUpdatedCallback(callback);
        return this;
    }


    public BindingManager getBindingManager() {
        return bindingManager;
    }

    @Override
    public DataUpdatedCallback getDataUpdatedCallback() {
        return bindingManager.getDataUpdatedCallback();
    }

    @Override
    public boolean hasDataUpdatedCallback() {
        return bindingManager.hasDataUpdatedCallback();
    }

    @Override
    public void setObject(Object o) {

    }

    public Context getContext() {
        return context;
    }

    public BindAdapter<O> setOnItemClickListener(OnItemClickListener<O> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void bindUI() {
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        updateDataSet();
        super.notifyDataSetChanged();
    }

    private void updateDataSet() {
        if (creatingBindersCount < getCount()) {
            createDataBindingSet();
        }
    }

    @Override
    public int getCount() {
        switch (mode) {
            case MODE_ARRAY:
                return os.length;
            case MODE_LIST:
                return itemList.size();
            default:
                return 0;
        }
    }

    @Override
    public O getItem(int position) {
        switch (mode) {
            case MODE_ARRAY:
                return os[position];
            case MODE_LIST:
                return itemList.get(position);
            default:
                return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    private void createDataBindingSet() {
        final Handler handler = new Handler();
        if (getCount() - creatingBindersCount > 1000) {
            Observable.just("create").subscribeOn(Schedulers.newThread()).observeOn(
                    Schedulers.newThread()
            ).subscribe(new Action1<String>() {
                @Override
                public void call(String index) {
                    for (int i = creatingBindersCount; i < getCount(); i++) {
                        getBinder(i);
                        creatingBindersCount++;
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            notifyDataSetChanged();
                        }
                    });
                }
            });
        } else {
            for (int i = creatingBindersCount; i < getCount(); i++) {
                getBinder(i);
                creatingBindersCount++;
            }
            notifyDataSetChanged();
        }
    }

    private View getDataBinderParentView(int position) {
        return getBinder(position).getViewParent();
    }


    private ObjectDataBinder getBinder(int position) {
        ObjectDataBinder dataBinder = ((ObjectDataBinder) getBindingManager().
                getBinder(String.valueOf(position)));
        if (dataBinder == null) {
            dataBinder = registerBinder(position);
        }
        return dataBinder;
    }


    protected ObjectDataBinder registerBinder(int position) {
        ObjectDataBinder dataBinder = getViewDataBinder(position);
        getBindingManager().newBinder(String.valueOf(position), dataBinder);
        return dataBinder;
    }

    protected ObjectDataBinder getViewDataBinder(int position) {
        View view = inflater.inflate(layoutResID, null);
        view.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                AbsListView.LayoutParams.WRAP_CONTENT));
        ObjectDataBinder dataBinder = new ObjectDataBinder<O>(getItem(position))
                .registerView(view);
        dataBinder.bindUI();
        return dataBinder;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view = getDataBinderParentView(position);
        view.getLayoutParams().width = parent.getMeasuredWidth();
        if (onItemClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position, getItem(position));
                }
            });
        }
        return view;
    }

    public interface OnItemClickListener<O> {
        void onItemClick(int position, O o);
    }
}
