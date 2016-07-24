package ru.moleculus.moveme.ui.activity;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.data.beans.Location;
import ru.moleculus.moveme.ui.fragments.orders.SetLocationFragment;

/**
 * Created by Oleg on 24.03.2016.
 */
public class SetLocationActivity extends BaseChildActivity {

    private Location location;
    private int position;

    @Override
    protected Fragment getChildFragment() {
        location = (Location) getIntent().getExtras().getSerializable(EXTRA_LOCATION);
        position = getIntent().getExtras().getInt(EXTRA_POSITION);
        return SetLocationFragment.newInstance(location);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (position > 2) {
            getMenuInflater().inflate(R.menu.menu_delete, menu);
            menu.findItem(R.id.action_delete).setVisible(location != null);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                setResult(RESULT_DELETE);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
