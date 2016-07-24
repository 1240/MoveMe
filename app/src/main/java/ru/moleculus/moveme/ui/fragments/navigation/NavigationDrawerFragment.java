package ru.moleculus.moveme.ui.fragments.navigation;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import ru.moleculus.moveme.R;
import ru.moleculus.moveme.customview.NavigationDrawerHeader;
import ru.moleculus.moveme.data.beans.User;
import ru.moleculus.moveme.ui.activity.SendNumberActivity;
import ru.moleculus.moveme.ui.fragments.user.AbsUserFragment;
import ru.moleculus.moveme.view.UserInfoView;

public class NavigationDrawerFragment extends AbsUserFragment implements UserInfoView, NavigationDrawerCallbacks, DrawerLayout.DrawerListener {
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerHeader mDrawerHeader;
    private DrawerListAdapter mDrawerAdapter;
    private ListView mDrawerListView;
    private View mFragmentContainerView;
    private int mCurrentSelectedPosition = -1;

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDrawerListView = (ListView) inflater.inflate(
                R.layout.drawer_main, container, false);
        initListView();
        return mDrawerListView;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUp(R.id.navigation_drawer,
                (DrawerLayout) getActivity().findViewById(R.id.drawer_layout));
    }


    @Override
    public void showUserData(User user) {
        super.showUserData(user);
        mDrawerAdapter.update(user);
        if(mCurrentSelectedPosition==-1) {
            selectItem(1);
        }
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerLayout.setDrawerListener(this);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
    }

    private void initListView() {
        mDrawerListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
        mDrawerHeader = new NavigationDrawerHeader(getContext());
        mDrawerListView.addHeaderView(mDrawerHeader);
        mDrawerAdapter = new DrawerListAdapter(getContext());
        mDrawerListView.setAdapter(mDrawerAdapter);
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        Fragment fragment = FragmentsFactory.makeFragment(User.getInstance(), position);
        if (fragment != null) {
            onNavigationDrawerItemSelected(fragment);
            mDrawerLayout.closeDrawers();
        }
    }


    @Override
    public void showErrorMessage(String message) {
        super.showErrorMessage(message);
        moveToLogin();
    }

    private void moveToLogin() {
        Intent intent = new Intent(getContext(), SendNumberActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public void onNavigationDrawerItemSelected(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onClickNavigationIcon() {
        openDrawer();
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        getPresenter().requestUser();
    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
