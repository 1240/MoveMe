package ru.moleculus.moveme.ui.activity;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.data.beans.Cargo;
import ru.moleculus.moveme.data.beans.Location;
import ru.moleculus.moveme.ui.fragments.orders.SetCargoFragment;
import ru.moleculus.moveme.ui.fragments.orders.SetLocationFragment;

/**
 * Created by Oleg on 24.03.2016.
 */
public class SetCargoActivity extends BaseChildActivity {

    private Cargo cargo;
    private int position;

    @Override
    protected Fragment getChildFragment() {
        cargo = (Cargo) getIntent().getExtras().getSerializable(EXTRA_CARGO);
        position = getIntent().getExtras().getInt(EXTRA_POSITION);
        return SetCargoFragment.newInstance(cargo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (position > 1) {
            getMenuInflater().inflate(R.menu.menu_delete, menu);
            menu.findItem(R.id.action_delete).setVisible(cargo != null);
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
