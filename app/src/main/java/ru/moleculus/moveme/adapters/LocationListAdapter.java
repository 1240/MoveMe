package ru.moleculus.moveme.adapters;

import android.content.Context;

import com.noisyz.databindinglibrary.annotations.field.SimpleFieldType;
import com.noisyz.databindinglibrary.annotations.type;
import com.noisyz.databindinglibrary.bind.base.impl.adapter.ConvertBindAdapter;

import java.util.List;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.data.beans.Location;

/**
 * Created by Oleg on 23.03.2016.
 */
public class LocationListAdapter extends ConvertBindAdapter<Location> {
    @SimpleFieldType(type.TEXT)
    private String title, address;

    public LocationListAdapter(Context context, List<Location> itemList) {
        super(context, itemList, R.layout.address_list_item);
    }

    @Override
    protected void updateFieldsByObject(int position, Location o) {
        title = getContext().getString(R.string.address) + " " + (position + 1);
        address = o.getAddress();
    }
}
