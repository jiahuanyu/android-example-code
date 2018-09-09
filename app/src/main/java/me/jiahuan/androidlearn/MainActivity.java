package me.jiahuan.androidlearn;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;

import java.util.List;

import me.jiahuan.androidlearn.base.BaseActivity;
import me.jiahuan.androidlearn.function.FunctionFragment;
import me.jiahuan.androidlearn.ui.UIFragment;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    BottomNavigationView mBottomNavigationView;

    private FunctionFragment mFunctionFragment;
    private UIFragment mUIFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        initializeActivity(R.layout.layout_main_activity);
        initialize();
    }

    private void initialize() {
        mBottomNavigationView = findViewById(R.id.id_layout_main_activity_bottom_navigation_view);
        mFunctionFragment = new FunctionFragment();
        mUIFragment = new UIFragment();
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                addOrShowFragment(item.getItemId());
                return true;
            }
        });
        addOrShowFragment(R.id.id_menu_main_activity_bottom_navigation_view_function);
    }


    private void hideAllFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (Fragment fragment : fragments) {
            fragmentTransaction.hide(fragment);
        }
        fragmentTransaction.commit();
    }

    private void addOrShowFragment(int itemId) {
        hideAllFragments();
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (itemId == R.id.id_menu_main_activity_bottom_navigation_view_function) {
            if (fragments.contains(mFunctionFragment)) {
                fragmentTransaction.show(mFunctionFragment);
            } else {
                fragmentTransaction.add(R.id.id_layout_main_activity_fragment_container_view_group, mFunctionFragment, FunctionFragment.TAG);
            }
            getSupportActionBar().setTitle("功能");
        } else if (itemId == R.id.id_menu_main_activity_bottom_navigation_view_ui) {
            if (fragments.contains(mUIFragment)) {
                fragmentTransaction.show(mUIFragment);
            } else {
                fragmentTransaction.add(R.id.id_layout_main_activity_fragment_container_view_group, mUIFragment, UIFragment.TAG);
            }
            getSupportActionBar().setTitle("UI");
        }
        fragmentTransaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}

