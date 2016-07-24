package ru.moleculus.moveme.ui.fragments.navigation;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ru.moleculus.moveme.BaseConstants;
import ru.moleculus.moveme.R;
import ru.moleculus.moveme.data.beans.User;

/**
 * Created by Oleg on 02.03.2016.
 */
public class DrawerListAdapter extends BaseAdapter {

    private ArrayList<DrawerItem> itemList;
    private Context context;

    public DrawerListAdapter(Context context) {
        this.context = context;
        itemList = new ArrayList<DrawerItem>();
    }

    public void update(User user){
        itemList.clear();
        String[] titles = null;
        TypedArray icons = null;
        switch (user.getTypeOfAccount()) {
            case BaseConstants.TYPE_ACCOUNT_CLIENT:
                titles = context.getResources().getStringArray(R.array.client_side_names);
                icons = context.getResources().obtainTypedArray(R.array.client_side_icons);
                break;
            case BaseConstants.TYPE_ACCOUNT_WORKER:
                titles = context.getResources().getStringArray(R.array.worker_side_names);
                icons = context.getResources().obtainTypedArray(R.array.worker_side_icons);
                break;
        }
        for (int i = 0; i < titles.length; i++) {
            itemList.add(new DrawerItem(
                    icons.getResourceId(i, 0), titles[i]
            ));
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {
        ImageView icon;
        TextView title;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.drawer_list_item, null);
            holder.icon = (ImageView) convertView.findViewById(R.id.drawer_item_icon);
            holder.title = (TextView) convertView.findViewById(R.id.drawer_item_title);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();
        DrawerItem item = (DrawerItem) getItem(position);
        holder.icon.setImageResource(item.getResourceId());
        holder.title.setText(item.getTitle());
        return convertView;
    }
}
